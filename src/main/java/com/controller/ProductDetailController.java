package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ProductDetailDTO;
import com.model.Product;
import com.model.Product_detail;
import com.repository.ProductDetailRepository;
import com.repository.ProductRepository;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class ProductDetailController {
    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductRepository productRepository;
    
    
    @GetMapping("/products/abc")
    public List<ProductDetailDTO> getAllProductDetails() {
        List<Product_detail> productDetails = productDetailRepository.findAll();
        List<ProductDetailDTO> productDetailDTOs = new ArrayList<>();
        
        for (Product_detail detail : productDetails) {
            Product product = productRepository.findById(detail.getProduct().getProduct_id()).orElse(null);
            if (product != null) {
                ProductDetailDTO dto = new ProductDetailDTO();
                dto.setProductId(product.getProduct_id());
                dto.setBrand(product.getP_brand());
                dto.setName(product.getP_name());
                dto.setPrice(product.getP_price());
                dto.setStatus(product.getP_status());
                dto.setComponent(detail.getP_component());
                dto.setGuide(detail.getP_guide());
                dto.setInstruction(detail.getP_instruction());
                dto.setMadeIn(detail.getP_madeIn());
                dto.setObject(detail.getP_object());
                dto.setPreservation(detail.getP_preservation());
                dto.setStore(detail.getP_store());
                dto.setVirtue(detail.getP_vitue());
                productDetailDTOs.add(dto);
            }
        }

        return productDetailDTOs;
    }
    
    
    
    
    
}
