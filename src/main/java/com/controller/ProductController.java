package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Product;
import com.repository.ProductRepository;
import com.service.ProductService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

//	@Autowired
//	private ProductService productService;

	@GetMapping("/products/test")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

//	@GetMapping("/products/search")
//	public ResponseEntity<List<Product>> searchProduct(@RequestParam("keyword") String keyword) {
//		List<Product> list = productService.searchProduct(keyword);
//		return ResponseEntity.ok(list);
//	}
//
//	 @GetMapping("/range")
//	    public ResponseEntity<List<Product>> getListProductByPriceRange(@RequestParam("id") long id,@RequestParam("min") int min, @RequestParam("max") int max){
//	        List<Product> list = productService.getListByRange(id, min, max);
//	        return ResponseEntity.ok(list);
//	    }
}