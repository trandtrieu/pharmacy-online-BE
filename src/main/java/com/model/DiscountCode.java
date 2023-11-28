package com.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "discountcode")
public class DiscountCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String code;
	
	@Column
	private double discountPercentage;
	
	@Column
	private LocalDateTime expiryDate;
	
	@Column
	private int timesUsable;
	
	@Column
	private int status;

	@Column
	private Long condition;

	@ManyToMany
	@JoinTable(name = "discount_account", joinColumns = @JoinColumn(name = "discount_id"), inverseJoinColumns = @JoinColumn(name = "account_id"))
	private Set<Account> accounts;

}
