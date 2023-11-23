package com.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscountCodeDTO {

	private Long id;

	private String code;

	private double discountPercentage;

	private LocalDateTime expiryDate;

	private int timesUsable;
	
	private int status;

	private Long condition;
}
