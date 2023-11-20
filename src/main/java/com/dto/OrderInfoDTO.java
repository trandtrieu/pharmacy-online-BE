package com.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO implements Serializable{
	private String amount;
    private String paymentMethod;
    private String deliveryMethod;
    private String name;
    private String phone;
    private String address;
}
