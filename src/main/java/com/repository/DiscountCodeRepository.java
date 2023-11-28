package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.DiscountCode;

public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Long> {
    DiscountCode findByCode(String code);
    
}

