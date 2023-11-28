package com.dto;

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

	private String username;

	private String password;

//	private String address;

	private String dob;

	private String avatar;

	private String phone;

	private String roles;

	private int status;

}
