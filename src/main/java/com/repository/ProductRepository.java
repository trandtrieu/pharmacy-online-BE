package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
