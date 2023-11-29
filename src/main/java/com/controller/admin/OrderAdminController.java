package com.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.dto.OrderInfoDTO;
import com.dto.ProductInfoDTO;
import com.model.OrderInfo;
import com.model.ProductInfo;
import com.repository.OrderRepository;

@CrossOrigin(origins = "http://localhost:3006")
@RestController
@RequestMapping("/pharmacy-online/admin/order")
public class OrderAdminController {

	@Autowired
	private OrderRepository orderRepository;

	@GetMapping("/list1")
	public ResponseEntity<List<OrderInfo>> listAllOrders() {
		List<OrderInfo> orders = orderRepository.findAll();
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/list")
	public ResponseEntity<List<OrderInfoDTO>> listOrders() {
		List<OrderInfo> orders = orderRepository.findAll();
		List<OrderInfoDTO> orderDTOs = new ArrayList<>();

		for (OrderInfo order : orders) {
			OrderInfoDTO orderDTO = new OrderInfoDTO();
			// Map fields from OrderInfo to OrderInfoDTO
			orderDTO.setId(order.getId());
			orderDTO.setAmount(order.getAmount());
			orderDTO.setPaymentMethod(order.getPaymentMethod());
			orderDTO.setDeliveryMethod(order.getDeliveryMethod());
			orderDTO.setName(order.getName());
			orderDTO.setPhone(order.getPhone());
			orderDTO.setAddress(order.getAddress());
			orderDTO.setAccountId(order.getAccountId());
			orderDTO.setNote(order.getNote());
			orderDTO.setDate(order.getDate());
			orderDTO.setStatus(order.getStatus());

			orderDTOs.add(orderDTO);
		}

		return ResponseEntity.status(HttpStatus.OK).body(orderDTOs);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderInfoDTO> getOrderById(@PathVariable Long orderId) {
		OrderInfo order = orderRepository.findById(orderId).orElse(null);
		if (order == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		OrderInfoDTO orderDTO = new OrderInfoDTO();
		orderDTO.setId(order.getId());
		orderDTO.setAmount(order.getAmount());
		orderDTO.setPaymentMethod(order.getPaymentMethod());
		orderDTO.setDeliveryMethod(order.getDeliveryMethod());
		orderDTO.setName(order.getName());
		orderDTO.setPhone(order.getPhone());
		orderDTO.setAddress(order.getAddress());
		orderDTO.setAccountId(order.getAccountId());
		orderDTO.setNote(order.getNote());
		orderDTO.setDate(order.getDate());
		orderDTO.setStatus(order.getStatus());

		return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
	}

	@GetMapping("/{orderId}/products")
	public ResponseEntity<List<ProductInfoDTO>> getProductsByOrderId(@PathVariable Long orderId) {
		Optional<OrderInfo> optionalOrder = orderRepository.findById(orderId);

		if (optionalOrder.isPresent()) {
			OrderInfo order = optionalOrder.get();
			List<ProductInfo> productInfoList = order.getProducts();

			List<ProductInfoDTO> productInfoDTOList = productInfoList.stream()
					.map(productInfo -> new ProductInfoDTO(productInfo.getNameproduct(), productInfo.getQuantity(),
							productInfo.getPrice()))
					.collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK).body(productInfoDTOList);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping("/update_order_status/{orderId}")
	public ResponseEntity<OrderInfoDTO> updateOrderStatus(@PathVariable Long orderId,
			@RequestBody Map<String, String> statusMap) {
		String newStatus = statusMap.get("status");

		if (newStatus == null || newStatus.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		OrderInfo order = orderRepository.findById(orderId).orElse(null);

		if (order == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		order.setStatus(newStatus);
		orderRepository.save(order);
		OrderInfoDTO orderDTO = new OrderInfoDTO();
		orderDTO.setId(order.getId());
		orderDTO.setAmount(order.getAmount());
		orderDTO.setPaymentMethod(order.getPaymentMethod());
		orderDTO.setDeliveryMethod(order.getDeliveryMethod());
		orderDTO.setName(order.getName());
		orderDTO.setPhone(order.getPhone());
		orderDTO.setAddress(order.getAddress());
		orderDTO.setAccountId(order.getAccountId());
		orderDTO.setNote(order.getNote());
		orderDTO.setDate(order.getDate());
		orderDTO.setStatus(order.getStatus());

		return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
	}

	@GetMapping("/listByAccountAndStatus/{accountId}/{status}")
	public ResponseEntity<List<OrderInfoDTO>> listOrdersByAccountAndStatus(@PathVariable String accountId,
			@PathVariable String status) {

		List<OrderInfo> orders = orderRepository.findByAccountIdAndStatus(accountId, status);

		if (orders.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		List<OrderInfoDTO> orderDTOs = orders.stream().map(order -> {
			OrderInfoDTO orderDTO = new OrderInfoDTO();
			orderDTO.setId(order.getId());
			orderDTO.setAmount(order.getAmount());
			orderDTO.setPaymentMethod(order.getPaymentMethod());
			orderDTO.setDeliveryMethod(order.getDeliveryMethod());
			orderDTO.setName(order.getName());
			orderDTO.setPhone(order.getPhone());
			orderDTO.setAddress(order.getAddress());
			orderDTO.setAccountId(order.getAccountId());
			orderDTO.setNote(order.getNote());
			orderDTO.setDate(order.getDate());
			orderDTO.setStatus(order.getStatus());

			return orderDTO;
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(orderDTOs);
	}
}
