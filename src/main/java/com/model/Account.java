package com.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
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

//	@OneToMany(mappedBy = "account")
//	private Set<Orders> Orders;

	@OneToMany(mappedBy = "account")
	private Set<Content> Contents;
	
	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private WishList wishList;

}
