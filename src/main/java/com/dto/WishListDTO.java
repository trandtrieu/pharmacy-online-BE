package com.dto;

import java.util.Date;

import com.model.Product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class WishListDTO {
	private int id;
	private Date createdDate;
	private Product product;
}
