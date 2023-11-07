package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Blog;
import com.model.Blog_img;

public interface BlogImageRepository extends JpaRepository<Blog_img, Integer> {
	List<Blog_img> findByBlog(Blog blog);

}
