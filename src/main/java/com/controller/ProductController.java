package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.repository.ProductRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
//	@GetMapping("/products")
//	public List<Product> getAllProducts(){
//		return productRepository.findAll();
//	}
	

}