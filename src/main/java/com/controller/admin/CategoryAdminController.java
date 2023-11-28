package com.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.CategoryDTO;
import com.model.Category;
import com.repository.CategoryRepository;
import com.service.CategoryService;

@CrossOrigin(origins = "http://localhost:3006")
@RestController
@RequestMapping("/pharmacy-online/admin/category")
public class CategoryAdminController {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/list")
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDTO> categoryDTOs = categories.stream().map(category -> {
			return new CategoryDTO(category.getCategory_id(), category.getCategory_name(), category.getCategory_img());
		}).collect(Collectors.toList());
		return categoryDTOs;
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int categoryId) {
		try {
			CategoryDTO categoryDTO = categoryService.getCategoryDTOById(categoryId);
			if (categoryDTO != null) {
				return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
//
//	@PostMapping("/create")
//	public ResponseEntity<String> createCategory(@RequestBody CategoryDTO categoryDTO) {
//		try {
//			Category newCategory = new Category();
//			newCategory.setCategory_name(categoryDTO.getCategory_name());
//			newCategory.setCategory_img(categoryDTO.getCategory_image());
//			categoryRepository.save(newCategory);
//			return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>("Failed to create category", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	@PostMapping("/create")
	public ResponseEntity<String> createCategory(
	    @RequestPart("categoryDTO") CategoryDTO categoryDTO,
	    @RequestPart(value = "file", required = false) MultipartFile file,
	    @RequestPart(value = "category_image_text", required = false) String categoryImageText
	) {
	    try {
	        Category newCategory = new Category();
	        newCategory.setCategory_name(categoryDTO.getCategory_name());

	        if (file != null) {
	            // Handling file upload
	            ResponseEntity<String> uploadResponse = uploadImage(file);
	            if (uploadResponse.getStatusCode() != HttpStatus.OK) {
	                return new ResponseEntity<>("Failed to upload image", uploadResponse.getStatusCode());
	            }
	            newCategory.setCategory_img(file.getOriginalFilename());

	        } else if (categoryImageText != null && !categoryImageText.isEmpty()) {
	            newCategory.setCategory_img(categoryImageText);

	        } else {
	            return new ResponseEntity<>("No image file or text provided", HttpStatus.BAD_REQUEST);
	        }
	        categoryRepository.save(newCategory);
	        return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Failed to create category", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@PostMapping("/uploadImage")
	public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
		if (imageFile != null) {
			String uploadFolderPath1 = "D:/Documents/OJT/mock-project/pharmacy-online-fe/public/assets/images";																									
			String uploadFolderPath2 = "D:/Documents/OJT/mock-project/pharmacy-online-admin/public/assets/images"; 
																												
			String fileName = imageFile.getOriginalFilename();

			try {
				Path imagePath1 = Paths.get(uploadFolderPath1, fileName);
				Files.write(imagePath1, imageFile.getBytes());

				Path imagePath2 = Paths.get(uploadFolderPath2, fileName);
				Files.write(imagePath2, imageFile.getBytes());

				return new ResponseEntity<>("Image uploaded successfully to both folders", HttpStatus.OK);
			} catch (IOException e) {
				return new ResponseEntity<>("Failed to upload the image", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>("No image file provided", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{categoryId}")
	public ResponseEntity<String> updateCategory(@PathVariable int categoryId,
			@RequestBody CategoryDTO updatedCategoryDTO) {
		try {
			if (!categoryRepository.existsById(categoryId)) {
				return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
			}
			Category existingCategory = categoryRepository.getById(categoryId);
			existingCategory.setCategory_name(updatedCategoryDTO.getCategory_name());
			existingCategory.setCategory_img(updatedCategoryDTO.getCategory_image());

			categoryRepository.save(existingCategory);

			return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to update category", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable int categoryId) {
		try {
			if (!categoryRepository.existsById(categoryId)) {
				return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
			}

			categoryRepository.deleteById(categoryId);

			return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to delete category", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}