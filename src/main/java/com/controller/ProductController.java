package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ProductDetailDTO;
import com.services.ProductService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class ProductController {
	
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody ProductDetailDTO productDetailDTO) {
        productService.addProduct(productDetailDTO);
        return ResponseEntity.ok("Sản phẩm đã được thêm thành công.");
    }
    
    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody ProductDetailDTO productDetails){
		productService.updateProduct(id, productDetails);
    	
		return ResponseEntity.ok("Sản phẩm đã được cập nhập thành công.");
    	
    }
	

	

}