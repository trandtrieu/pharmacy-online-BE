package com.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "mail")
	private String mail;

	@Column(name = "password")
	private String password;

	@Column(name = "address")
	private String address;

	@Column(name = "dob")
	private String dob;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "phone")
	private String phone;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@OneToMany(mappedBy = "account")
	private Set<Orders> Orders;

	@OneToMany(mappedBy = "account")
	private Set<Content> Contents;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private WishList wishList;


	public Account(long id, String name, String mail, String password, String address, String dob, String avatar,
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


	public Account() {
		super();
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
