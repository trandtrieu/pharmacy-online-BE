package com.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ProductDetailDTO {
	private int productId;
	private String brand;
	private String name;
	private BigDecimal price;
	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date createdDate;
	private int status;
	private int quantity;
	private int type;
	private int category_id;
	private String category_name;
	private String Ingredients;
	private String Indications;
	private String Contraindications;
	private String DosageAndUsage;
	private String SideEffects;
	private String Precautions;
	private String DrugInteractions;
	private String Storage;
	private String Packaging;
	private String MadeIn;
	private List<String> imageUrls;
	
	
	
	public ProductDetailDTO(int productId, String name) {
		super();
		this.productId = productId;
		this.name = name;
	}

	public ProductDetailDTO(int productId, String name, String brand, BigDecimal price) {
		super();
		this.productId = productId;
		this.brand = brand;
		this.name = name;
		this.price = price;
	}


	
	
}
