package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Product;
import com.model.Product_detail;

public interface ProductDetailRepository extends JpaRepository<Product_detail, Integer> {

	Product_detail findByProduct(Product product);

	@Query("SELECT p FROM Product p WHERE p.category.category_id = :category_id")
    Product_detail findProductsByCategoryId(@Param("category_id") Long categoryId);	
	
}
