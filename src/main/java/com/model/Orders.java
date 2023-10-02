package com.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "u_id", referencedColumnName = "id")
	private Account account;

	public Orders() {
		super();
	}

	public Orders(int id, String o_totalPrice, String o_address, String o_note, int ship_id) {
		super();
		this.id = id;
		this.o_totalPrice = o_totalPrice;
		this.o_address = o_address;
		this.o_note = o_note;
		this.ship_id = ship_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getO_totalPrice() {
		return o_totalPrice;
	}

	public void setO_totalPrice(String o_totalPrice) {
		this.o_totalPrice = o_totalPrice;
	}

	public String getO_address() {
		return o_address;
	}

	public void setO_address(String o_address) {
		this.o_address = o_address;
	}

	public String getO_note() {
		return o_note;
	}

	public void setO_note(String o_note) {
		this.o_note = o_note;
	}

	public int getShip_id() {
		return ship_id;
	}

	public void setShip_id(int ship_id) {
		this.ship_id = ship_id;
	}

}
