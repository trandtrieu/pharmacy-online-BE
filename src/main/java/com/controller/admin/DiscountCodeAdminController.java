package com.controller.admin;

import com.dto.DiscountCodeDTO;
import com.service.DiscountCodeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/pharmacy-online/admin/discount-code")
@CrossOrigin(origins = "http://localhost:3006")
public class DiscountCodeAdminController {

    @Autowired
    DiscountCodeService discountCodeService;

    @GetMapping(path = "/generate-code")
    public ResponseEntity<String> generateDiscountCode() {
        return ResponseEntity.ok(discountCodeService.generateDiscountCode());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DiscountCodeDTO> getDiscountCodeById(@PathVariable Long id){
        return ResponseEntity.ok( discountCodeService.getDiscountCode(id));
    }


    @PostMapping(path = "/create-discountcode")
    public ResponseEntity<?> createDiscountCode(@RequestBody DiscountCodeDTO discountCodeDTO) {
        return ResponseEntity.ok(discountCodeService.createDiscountCode(discountCodeDTO));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<DiscountCodeDTO>> getListDiscountCode() {
        return ResponseEntity.ok(discountCodeService.getAllDiscountCode());
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteDiscount(@PathVariable Long id) {
        discountCodeService.deleteDiscountCode(id);
        return ResponseEntity.ok("Deleted");
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> updateDiscount(@PathVariable Long id, @RequestBody DiscountCodeDTO discountCodeDTO) {
        discountCodeService.updateDiscountCodeT(discountCodeDTO, id);
        return ResponseEntity.ok("Data save");
    }

}
