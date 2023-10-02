package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name ="product_detail")
public class Product_detail {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int detail_id;
	
	
	@Column
	private String p_component;
	
	@Column
	private String p_vitue;
	
	@Column
	private String p_object;
	
	@Column
	private String p_guide;
	
	@Column
	private String p_preservation;
		
	@Column
	private String p_instruction;
	
	@Column 
	private String p_store;
	
	@Column 
	private String p_madeIn;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
	
	
	public Product_detail() {
		super();
	}



	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getP_component() {
		return p_component;
	}

	public void setP_component(String p_component) {
		this.p_component = p_component;
	}

	public String getP_vitue() {
		return p_vitue;
	}

	public void setP_vitue(String p_vitue) {
		this.p_vitue = p_vitue;
	}

	public String getP_object() {
		return p_object;
	}

	public void setP_object(String p_object) {
		this.p_object = p_object;
	}

	public String getP_guide() {
		return p_guide;
	}

	public void setP_guide(String p_guide) {
		this.p_guide = p_guide;
	}

	public String getP_preservation() {
		return p_preservation;
	}

	public void setP_preservation(String p_preservation) {
		this.p_preservation = p_preservation;
	}

	public String getP_instruction() {
		return p_instruction;
	}

	public void setP_instruction(String p_instruction) {
		this.p_instruction = p_instruction;
	}

	public String getP_store() {
		return p_store;
	}

	public void setP_store(String p_store) {
		this.p_store = p_store;
	}

	public String getP_madeIn() {
		return p_madeIn;
	}

	public void setP_madeIn(String p_madeIn) {
		this.p_madeIn = p_madeIn;
	}
	

	
	
	
}
