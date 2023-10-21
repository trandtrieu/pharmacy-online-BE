package com.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "prescription")
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String note;

	@Column
	private String imageUrls;

	@Column
	private int status;

	@Column
	private String name;

	@Column
	private String phone;
	
	
	@Column
	private String email;

	@Column
	private LocalDate createdDate;

	@Column
	private LocalTime createdTime;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

}
