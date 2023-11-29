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

import java.util.List;

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

    @GetMapping("/th/{id}")
    public ResponseEntity<DiscountCodeDTO> getDiscountCodeDTOByIdTh(@PathVariable Long id){
        return ResponseEntity.ok(discountCodeService.getDiscountCode(id));
    }

    @GetMapping("/dtoa/{did}/{aid}")
    public ResponseEntity<String> addDiscountToAccount(@PathVariable Long did, @PathVariable Long aid){
        discountCodeService.addDiscountToAccount(did, aid);
        return ResponseEntity.ok("Data saved");
    }

    @GetMapping("/allDiscount")
    public ResponseEntity<List<DiscountCodeDTO>> getAllDiscount(){
        return ResponseEntity.ok(discountCodeService.getAllDiscountCode());
    }

}