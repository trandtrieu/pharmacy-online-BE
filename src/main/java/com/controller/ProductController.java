package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Product;
import com.repository.ProductRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/products/test")
	public List<Product> getAllProducts() {

		return productRepository.findAll();
	}
	

}