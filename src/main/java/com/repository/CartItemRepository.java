package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.CartItem;
import com.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	CartItem findByProduct(Product product);
}
