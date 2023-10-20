package com.dto;

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

	private int id;

	private String note;

	private String imageUrls;

	private int status;

	private Account account;
	
	private String name;

	private long account_id;
	
	public PrescriptionDTO(int id, String note, String imageUrls, int status) {
		super();
		this.id = id;
		this.note = note;
		this.imageUrls = imageUrls;
		this.status = status;
	}

	public PrescriptionDTO(int id, String note, String imageUrls, int status, String name) {
		super();
		this.id = id;
		this.note = note;
		this.imageUrls = imageUrls;
		this.status = status;
		this.name = name;
	}

	public PrescriptionDTO(int id, String note, String imageUrls, int status, String name, long account_id) {
		super();
		this.id = id;
		this.note = note;
		this.imageUrls = imageUrls;
		this.status = status;
		this.name = name;
		this.account_id = account_id;
	}

}
