package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Product;
import com.model.Product_image;

import jakarta.transaction.Transactional;

public interface ProductImageRepository extends JpaRepository<Product_image, Integer> {

	List<Product_image> findByProduct(Product product);

}
