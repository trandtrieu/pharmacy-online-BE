package com.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;





@Entity
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int product_id;
	@Column
	private String p_name;
	@Column
	private BigDecimal p_price;
	@Column
	private String p_brand;
	@Column
	private int p_status;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Product_image> images = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    private Set<WishList> wishLists = new HashSet<>();

	public Product(int product_id, String p_name, BigDecimal p_price, String p_brand, int p_status, Category category,
			List<Product_image> images, Set<WishList> wishLists) {
		super();
		this.product_id = product_id;
		this.p_name = p_name;
		this.p_price = p_price;
		this.p_brand = p_brand;
		this.p_status = p_status;
		this.category = category;
		this.images = images;
		this.wishLists = wishLists;
	}

	public Product() {
		super();
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public BigDecimal getP_price() {
		return p_price;
	}

	public void setP_price(BigDecimal p_price) {
		this.p_price = p_price;
	}

	public String getP_brand() {
		return p_brand;
	}

	public void setP_brand(String p_brand) {
		this.p_brand = p_brand;
	}

	public int getP_status() {
		return p_status;
	}

	public void setP_status(int p_status) {
		this.p_status = p_status;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Product_image> getImages() {
		return images;
	}

	public void setImages(List<Product_image> images) {
		this.images = images;
	}

	public Set<WishList> getWishLists() {
		return wishLists;
	}

	public void setWishLists(Set<WishList> wishLists) {
		this.wishLists = wishLists;
	}

}
