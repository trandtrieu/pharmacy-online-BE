package com.dto;

import java.util.Set;

import com.model.Content;
import com.model.Orders;
import com.model.WishList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class AccountDTO {

	private long id;

	private String name;

	private String mail;

	private String password;

	private String address;

	private String dob;

	private String avatar;

	private String phone;

	private String role;

	private Set<Orders> Orders;

	private Set<Content> Contents;

	private WishList wishList;

//	private Cart cart;

	public AccountDTO(long id, String name, String mail, String password, String address, String dob, String avatar,
			String phone, String role, Set<com.model.Orders> orders, Set<Content> contents, WishList wishList) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.password = password;
		this.address = address;
		this.dob = dob;
		this.avatar = avatar;
		this.phone = phone;
		this.role = role;
		Orders = orders;
		Contents = contents;
		this.wishList = wishList;
	}

	public AccountDTO(long id, String name, String mail, String password, String address, String dob, String avatar,
			String phone, String role) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.password = password;
		this.address = address;
		this.dob = dob;
		this.avatar = avatar;
		this.phone = phone;
		this.role = role;
	}

	public AccountDTO(long id, String name, String mail, String password, String address, String dob, String avatar,
			String phone, String Role, Set<Content> contents) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.password = password;
		this.address = address;
		this.dob = dob;
		this.avatar = avatar;
		this.phone = phone;
		this.role = Role;
		Contents = contents;
	}

	public AccountDTO(long id, String name, String mail, String password, String address, String dob, String avatar,
			String phone, String role, Set<com.model.Orders> orders, Set<Content> contents) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.password = password;
		this.address = address;
		this.dob = dob;
		this.avatar = avatar;
		this.phone = phone;
		this.role = role;
		Orders = orders;
		Contents = contents;
	}

	public AccountDTO(long id, String name, String mail, String password, String address, String dob, String avatar,
			String phone, String role, Set<Content> contents, WishList wishList) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.password = password;
		this.address = address;
		this.dob = dob;
		this.avatar = avatar;
		this.phone = phone;
		this.role = role;
		Contents = contents;
		this.wishList = wishList;
	}
	
	

}
