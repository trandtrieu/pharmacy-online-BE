package com.model;

import java.util.List;
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
	
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Feedback> feedbackList;

	
	
	
	

}
