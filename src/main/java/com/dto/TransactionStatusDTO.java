package com.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class TransactionStatusDTO implements Serializable{
	private String status;
	private String message;
	private String data;

}
