package com.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="category")
public class Category {
		
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

	private int category_id;
	
	@Column
	private String category_name;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

	public Category() {
		super();
	}

	public Category(int category_id, String category_name, List<Product> products) {
		super();
		this.category_id = category_id;
		this.category_name = category_name;
		this.products = products;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Category [category_id=" + category_id + ", category_name=" + category_name + ", products=" + products
				+ "]";
	}
	
	
	
	
}
