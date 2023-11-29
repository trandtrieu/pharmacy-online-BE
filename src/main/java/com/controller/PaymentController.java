package com.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.config.Config;
import com.dto.PaymentRequest;
import com.dto.PaymentResDTO;
import com.dto.ProductInfoDTO;
import com.dto.TransactionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.model.OrderInfo;
import com.model.ProductInfo;
import com.model.Transaction;
import com.repository.OrderRepository;
import com.repository.TransactionRepository;
import com.service.TransactionService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

	@Autowired
	TransactionService transactionService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private OrderRepository orderRepository;

	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	String formattedDate = dateFormat.format(date);

	@PostMapping("/create_payment")
	public ResponseEntity<?> createPayment(HttpServletRequest req, @RequestBody PaymentRequest paymentRequest)
			throws UnsupportedEncodingException, JsonMappingException, JsonProcessingException {

		OrderInfo order = new OrderInfo();
		order.setAccountId(paymentRequest.getAccountId());
		order.setAddress(paymentRequest.getAddress());
		order.setAmount(paymentRequest.getAmount());
		order.setDeliveryMethod(paymentRequest.getDeliveryMethod());
		order.setName(paymentRequest.getName());
		order.setNote(paymentRequest.getNote());
		order.setPaymentMethod(paymentRequest.getPaymentMethod());
		order.setPhone(paymentRequest.getPhone());
		order.setDate(formattedDate);
		order.setStatus("Wait for confirmation");
		List<ProductInfoDTO> productInfoDTOList = paymentRequest.getProducts();
		System.out.println("Debug: ProductInfoDTOList - " + productInfoDTOList);
		List<ProductInfo> productInfoList = new ArrayList<>();

		for (ProductInfoDTO productInfoDTO : productInfoDTOList) {
			ProductInfo productInfo = new ProductInfo();
			productInfo.setNameproduct(productInfoDTO.getNameproduct());
			productInfo.setQuantity(productInfoDTO.getQuantity());
			productInfo.setPrice(productInfoDTO.getPrice());

			productInfo.setOrderInfo(order);
			productInfoList.add(productInfo);
		}

		order.setProducts(productInfoList);

		OrderInfo result = orderRepository.save(order);

		String orderType = "other";
		long amount = Integer.parseInt(paymentRequest.getAmount()) * 100;
//		long amount = 100000;
		String vnp_TxnRef = Config.getRandomNumber(8);
		String vnp_IpAddr = Config.getIpAddress(req);
		String vnp_TmnCode = Config.vnp_TmnCode;

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", Config.vnp_Version);
		vnp_Params.put("vnp_Command", Config.vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");
		vnp_Params.put("vnp_CreateDate", "formattedDate");
		vnp_Params.put("vnp_Locale", "vn");
//        if (bankCode != null && !bankCode.isEmpty()) {
//            vnp_Params.put("vnp_BankCode", bankCode);
//        }
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", result.getId().toString());
		vnp_Params.put("vnp_OrderType", orderType);

//        String locate = req.getParameter("language");
//        if (locate != null && !locate.isEmpty()) {
//            vnp_Params.put("vnp_Locale", locate);
//        } else {
//           
//        } 
		vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

		PaymentResDTO paymentResDTO = new PaymentResDTO();
		paymentResDTO.setStatus("ok");
		paymentResDTO.setMessage("Successfully");
		paymentResDTO.setURL(paymentUrl);
		System.out.println(paymentUrl);

		return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);
	}

	@PostMapping("/save-url")
	public ResponseEntity<Transaction> saveUrl(@RequestBody TransactionDTO dto) {
		Transaction entity = new Transaction();
		entity.setAddress(dto.getAddress());
		entity.setAmount(dto.getAmount());
		entity.setDeliveryMethod(dto.getDeliveryMethod());
		entity.setName(dto.getName());
		entity.setPaymentMethod(dto.getPaymentMethod());
		System.out.println(dto.getPaymentMethod());
		entity.setPhone(dto.getPhone());
		entity.setNote(dto.getNote());
		entity.setStatus("Successfully");
		Transaction result = transactionRepository.save(entity);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/order/{orderId}")
	public OrderInfo getOrderById(@PathVariable Long orderId) {
		OrderInfo result = orderRepository.findById(orderId).orElse(null);

		System.out.println(result);

		return result;
	}

}
