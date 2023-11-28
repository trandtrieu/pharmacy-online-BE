package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.CategoryDTO;
import com.model.Category;
import com.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	// Other methods in your service...

	public CategoryDTO getCategoryDTOById(int categoryId) {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

		if (optionalCategory.isPresent()) {
			Category category = optionalCategory.get();
			// Create and return a CategoryDTO based on the Category entity
			return new CategoryDTO(category.getCategory_id(), category.getCategory_name(),
					category.getCategory_img());
		} else {
			return null;
		}
	}

	// Other methods in your service...
}
