package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

}
