package com.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CategoryDTO;
import com.model.Category;
import com.repository.CategoryRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;
	
	// get all category
	@GetMapping("/category")
	public List<CategoryDTO> getAllCategory(){
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDTO> categoryDTOs = categories.stream().map(category -> {
			return new CategoryDTO(category.getCategory_id(), category.getCategory_name());
		}).collect(Collectors.toList());
		return categoryDTOs;
	}
	
}
