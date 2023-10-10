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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

	@OneToOne(mappedBy = "product")
	private Product_detail product_detail;

	@ManyToMany(mappedBy = "products")
	private Set<WishList> wishLists = new HashSet<>();

//    @ManyToMany(mappedBy = "products")
//    private Set<Cart> carts = new HashSet<>();

}
