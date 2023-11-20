package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
	private Long amount;
	private String paymentMethod;
	private String deliveryMethod;
	private String name;
	private String address;
	private String phone;
	private String note;
}
