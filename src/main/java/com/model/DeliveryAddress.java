package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Delivery_Address")
public class DeliveryAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int address_id;

	@Column(columnDefinition = "nvarchar(MAX)")
	private String recipient_full_name;

	@Column(columnDefinition = "nvarchar(MAX)")
	private String recipient_phone_number;

	@Column(columnDefinition = "nvarchar(MAX)")
	private String specific_address;
	
	@Column
	private int status_default;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Account account;
}