package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{
    Optional<Cart> findByAccountId(Long accountId);

}
