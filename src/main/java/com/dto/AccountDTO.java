package com.dto;

import java.util.Set;

import com.model.Content;
import com.model.Orders;
import com.model.Role;
import com.model.WishList;

public class AccountDTO {

	private long id;

	private String name;

	private String mail;

	private String password;

	private String address;

	private String dob;

	private String avatar;

	private String phone;

	private Role role;

	private Set<Orders> Orders;

	private Set<Content> Contents;

	private WishList wishList;

	public AccountDTO() {
		super();
	}

	public AccountDTO(long id, String name, String mail, String password, String address, String dob, String avatar,
			String phone, Role role, Set<com.model.Orders> orders, Set<Content> contents, WishList wishList) {
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
			String phone, Role role) {
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
			String phone, Role role, Set<Content> contents) {
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
	}

	public AccountDTO(long id, String name, String mail, String password, String address, String dob, String avatar,
			String phone, Role role, Set<com.model.Orders> orders, Set<Content> contents) {
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
			String phone, Role role, Set<Content> contents, WishList wishList) {
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Set<Orders> getOrders() {
		return Orders;
	}

	public void setOrders(Set<Orders> orders) {
		Orders = orders;
	}

	public Set<Content> getContents() {
		return Contents;
	}

	public void setContents(Set<Content> contents) {
		Contents = contents;
	}

	public WishList getWishList() {
		return wishList;
	}

	public void setWishList(WishList wishList) {
		this.wishList = wishList;
	}

}
