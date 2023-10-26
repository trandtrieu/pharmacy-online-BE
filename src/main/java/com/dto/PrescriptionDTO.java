package com.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.model.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PrescriptionDTO {

	private long id;

	private String note;

	private String imageUrls;

	private int status;

	private Account account;

	private String name;

	private String phone;

	private String email;

	private long account_id;

	private LocalDate createdDate;

	private String createdTime;

	private LocalDate updatedDate;
	
	private String updatedTime;

	public PrescriptionDTO(long id, String note, String imageUrls, int status, String name, long account_id,
			LocalDate createdDate, LocalTime createdTime) {
		super();
		this.id = id;
		this.note = note;
		this.imageUrls = imageUrls;
		this.status = status;
		this.name = name;
		this.account_id = account_id;
		this.createdDate = createdDate;
		this.createdTime = createdTime.format(DateTimeFormatter.ofPattern("HH:mm"));
	}

	public PrescriptionDTO(long id, String note, String imageUrls, int status, String name, long account_id,
			String phone, String email) {
		super();
		this.id = id;
		this.note = note;
		this.imageUrls = imageUrls;
		this.status = status;
		this.name = name;
		this.account_id = account_id;
		this.phone = phone;
		this.email = email;

	}

	public PrescriptionDTO(long id, String note, String imageUrls, int status, String name, String phone, String email,
			long account_id, LocalDate createdDate, LocalTime createdTime) {
		super();
		this.id = id;
		this.note = note;
		this.imageUrls = imageUrls;
		this.status = status;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.account_id = account_id;
		this.createdDate = createdDate;
		this.createdTime = createdTime.format(DateTimeFormatter.ofPattern("HH:mm"));
	}

	public PrescriptionDTO(long id, String note, String imageUrls, int status, String name, String phone, String email,
			long account_id, LocalDate createdDate, LocalTime createdTime, LocalDate updatedDate, LocalTime updatedTime) {
		super();
		this.id = id;
		this.note = note;
		this.imageUrls = imageUrls;
		this.status = status;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.account_id = account_id;
		this.createdDate = createdDate;
		this.createdTime = createdTime.format(DateTimeFormatter.ofPattern("HH:mm"));
		this.updatedDate = updatedDate;
		this.updatedTime = updatedTime.format(DateTimeFormatter.ofPattern("HH:mm"));
	}

}
