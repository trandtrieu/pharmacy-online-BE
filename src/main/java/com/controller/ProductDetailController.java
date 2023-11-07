package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ProductDetailDTO;
import com.model.Product;
import com.model.Product_detail;
import com.model.Product_image;
import com.repository.ProductDetailRepository;
import com.repository.ProductImageRepository;
import com.repository.ProductRepository;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class ProductDetailController {
    @Autowired
    private ProductDetailRepository productDetailRepository;

    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;
    
    
    //show list product 
    @GetMapping("/products")
    public List<ProductDetailDTO> getAllProductsWithDetailsAndImages() {
        List<ProductDetailDTO> productDTOs = new ArrayList<>();

        List<Product> products = productRepository.findAll();
        for (Product product : products) {
//            Product_detail productDetail =  productDetailRepository.findByProduct(product);
            List<Product_image> productImages = productImageRepository.findByProduct(product);

            ProductDetailDTO productDTO = new ProductDetailDTO();
            productDTO.setProductId(product.getProduct_id());
            productDTO.setBrand(product.getP_brand());
            productDTO.setName(product.getP_name());
            productDTO.setPrice(product.getP_price());
            productDTO.setStatus(product.getP_status());

            productDTO.setCategory_id(product.getCategory().getCategory_id());
            String categoryName = product.getCategory().getCategory_name();
            productDTO.setCategory_name(categoryName);
//
//            if (productDetail != null) {
//                productDTO.setComponent(productDetail.getP_component());
//                productDTO.setGuide(productDetail.getP_guide());
//                productDTO.setInstruction(productDetail.getP_instruction());
//                productDTO.setMadeIn(productDetail.getP_madeIn());
//                productDTO.setObject(productDetail.getP_object());
//                productDTO.setPreservation(productDetail.getP_preservation());
//                productDTO.setStore(productDetail.getP_store());
//                productDTO.setVirtue(productDetail.getP_vitue());
//            }

            List<String> imageUrls = new ArrayList<>();
            for (Product_image productImage : productImages) {
                imageUrls.add(productImage.getImageUrl());
            }
            productDTO.setImageUrls(imageUrls);

            productDTOs.add(productDTO);
        }

        return productDTOs;
    }
    
    



    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDetailDTO> getProductDetailsById(@PathVariable Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);
        
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        ProductDetailDTO productDTO = new ProductDetailDTO();
        productDTO.setProductId(product.getProduct_id());
        productDTO.setBrand(product.getP_brand());
        productDTO.setName(product.getP_name());
        productDTO.setPrice(product.getP_price());
        productDTO.setStatus(product.getP_status());
        productDTO.setCategory_id(product.getCategory().getCategory_id());
        String categoryName = product.getCategory().getCategory_name();
        productDTO.setCategory_name(categoryName);
        Product_detail productDetail = productDetailRepository.findByProduct(product);
        if (productDetail != null) {
            productDTO.setComponent(productDetail.getP_component());
            productDTO.setGuide(productDetail.getP_guide());
            productDTO.setInstruction(productDetail.getP_instruction());
            productDTO.setMadeIn(productDetail.getP_madeIn());
            productDTO.setObject(productDetail.getP_object());
            productDTO.setPreservation(productDetail.getP_preservation());
            productDTO.setStore(productDetail.getP_store());
            productDTO.setVirtue(productDetail.getP_vitue());
        }

        List<Product_image> productImages = productImageRepository.findByProduct(product);
        List<String> imageUrls = new ArrayList<>();
        for (Product_image productImage : productImages) {
            imageUrls.add(productImage.getImageUrl());
        }
        productDTO.setImageUrls(imageUrls);

        return ResponseEntity.ok(productDTO);
    }

}
