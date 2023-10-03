package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Product;
import com.model.Product_detail;

public interface ProductDetailRepository extends JpaRepository<Product_detail, Integer> {

	Product_detail findByProduct(Product product);

}