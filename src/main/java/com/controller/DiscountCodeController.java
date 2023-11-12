package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.DiscountCodeDTO;
import com.model.DiscountCode;
import com.service.DiscountCodeService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/pharmacy-online/discount-code")
public class DiscountCodeController {

	@Autowired
	private DiscountCodeService discountCodeService;

	@PostMapping("/create")
	public ResponseEntity<DiscountCode> createDiscountCode(@RequestBody DiscountCodeDTO discountCodeDTO) {
		DiscountCode discountCode = discountCodeService.createDiscountCode(discountCodeDTO);
		return ResponseEntity.ok(discountCode);
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<DiscountCode> getDiscountCodeById(@PathVariable Long id) {
        DiscountCode discountCode = discountCodeService.getDiscountCodeById(id);
        
        if (discountCode != null) {
            return ResponseEntity.ok(discountCode);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    


}