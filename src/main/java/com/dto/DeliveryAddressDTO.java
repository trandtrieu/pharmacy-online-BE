package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressDTO {
	private int address_id;
	private String recipient_full_name;
	private String recipient_phone_number;
	private String specific_address;
	private long user_id;


}
