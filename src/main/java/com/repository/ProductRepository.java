package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Product;

import jakarta.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
