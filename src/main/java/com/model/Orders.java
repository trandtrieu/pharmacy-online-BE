package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "o_totalPrice")
	private String o_totalPrice;

	@Column(name = "o_address")
	private String o_address;

	@Column(name = "o_note")
	private String o_note;

	@Column(name = "ship_id")
	private int ship_id;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "u_id", referencedColumnName = "id")
//	private Account account;


}
