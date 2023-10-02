package com.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Product;
import com.repository.ProductRepository;

@RestController
@RequestMapping("/pharmacy-online/")
public class ProductController {
	
	
	private ProductRepository productRepository;
	@GetMapping("/products")
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
}
