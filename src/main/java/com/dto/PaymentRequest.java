package com.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
	private String accountId;
	private String amount;
	private String paymentMethod;
	private String deliveryMethod;
	private String name;
	private String phone;
	private String address;
	private String note;
	private List<ProductInfoDTO> products;
}