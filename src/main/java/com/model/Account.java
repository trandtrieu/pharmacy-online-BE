package com.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "mail")
	private String mail;

	@Column(name = "password")
	private String password;

	@Column(name = "dob")
	private String dob;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "phone")
	private String phone;

	@Column(name = "roles")
	private String roles;

	@Column(name = "status")
	private int status;

	@Column
	private String account_image;

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private WishList wishList;

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Cart cart;

	@OneToMany(mappedBy = "account")
	private List<Prescription> prescriptions;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Feedback> feedbackList;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Reply> Replys;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<DeliveryAddress> address;

	@ManyToMany(mappedBy = "accounts")
	private Set<DiscountCode> discountCodes;


}
