package com.model;

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
    private Product product;


    
    

}
