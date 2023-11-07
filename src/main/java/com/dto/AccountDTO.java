package com.dto;

import java.util.Set;

import com.model.Content;
import com.model.Orders;
import com.model.Role;
import com.model.WishList;

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

//	private String address;

	private String dob;

	private String avatar;

	private String phone;

	private Role role;

	private Set<Orders> Orders;

	private Set<Content> Contents;

	private WishList wishList;

//	private Cart cart;

	public AccountDTO(long id, String name, String mail, String password, String dob, String avatar,
			String phone, Role role, Set<com.model.Orders> orders, Set<Content> contents, WishList wishList) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.password = password;
//		this.address = address;
		this.dob = dob;
		this.avatar = avatar;
		this.phone = phone;
		this.role = role;
		Orders = orders;
		Contents = contents;
		this.wishList = wishList;
	}

	public AccountDTO(long id, String name, String mail, String password, String dob, String avatar,
			String phone, Role role) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.password = password;
//		this.address = address;
		this.dob = dob;
		this.avatar = avatar;
		this.phone = phone;
		this.role = role;
	}

	public AccountDTO(long id, String name, String mail, String password, String dob, String avatar,
			String phone, Role role, Set<Content> contents) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.password = password;
		this.dob = dob;
		this.avatar = avatar;
		this.phone = phone;
		this.role = role;
		Contents = contents;
	}

	public AccountDTO(long id, String name, String mail, String password, String dob, String avatar,
			String phone, Role role, Set<com.model.Orders> orders, Set<Content> contents) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.password = password;

		this.dob = dob;
		this.avatar = avatar;
		this.phone = phone;
		this.role = role;
		Orders = orders;
		Contents = contents;
	}

	public AccountDTO(long id, String name, String mail, String password, String dob, String avatar,
			String phone, Role role, Set<Content> contents, WishList wishList) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.password = password;
		this.dob = dob;
		this.avatar = avatar;
		this.phone = phone;
		this.role = role;
		Contents = contents;
		this.wishList = wishList;
	}

}
