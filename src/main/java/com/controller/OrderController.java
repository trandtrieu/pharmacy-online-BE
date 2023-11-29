package com.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.OrderInfoDTO;
import com.model.OrderInfo;
import com.repository.OrderRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

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
