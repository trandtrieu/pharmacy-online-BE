package com.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO implements Serializable{
	private Long id;
	private String amount;
    private String paymentMethod;
    private String deliveryMethod;
    private String name;
    private String phone;
    private String address; 
	private String accountId;
    private String note;
    private String date;
    private String status;
}
