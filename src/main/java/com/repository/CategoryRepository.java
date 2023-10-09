package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Category;

import jakarta.transaction.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
