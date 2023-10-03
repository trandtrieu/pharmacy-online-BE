package com.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import com.model.Product;
import com.repository.ProductRepository;
=======

>>>>>>> 6fa192763f2fa746b55cbd023bb44b284d1a25c7

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class ProductController {
	
<<<<<<< HEAD
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/products")
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	
	
	
=======

>>>>>>> 6fa192763f2fa746b55cbd023bb44b284d1a25c7
}
