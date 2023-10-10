package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_image")
public class Product_image  {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int image_id;
	
	@Column
	private String imageUrl;
	
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

	public int getImage_id() {
		return image_id;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product_image(int image_id, String imageUrl, Product product) {
		super();
		this.image_id = image_id;
		this.imageUrl = imageUrl;
		this.product = product;
	}

	public Product_image() {
		super();
	}
}
