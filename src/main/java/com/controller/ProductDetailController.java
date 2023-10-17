package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dto.ProductDetailDTO;
import com.model.Product;
import com.model.Product_detail;
import com.model.Product_image;
import com.repository.ProductDetailRepository;
import com.repository.ProductImageRepository;
import com.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/products")
    public List<ProductDetailDTO> getAllProductsWithDetailsAndImages() {
        List<Product> products = productRepository.findAll();

        return products.stream()
            .map(product -> createProductDetailDTO(product))
            .collect(Collectors.toList());
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDetailDTO> getProductDetailsById(@PathVariable Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        ProductDetailDTO productDTO = createProductDetailDTO(product);

        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/products/category")
    public List<ProductDetailDTO> getProductsByCategoryId(@RequestParam("category_id") Long category_id) {
        List<Product> products = productRepository.findProductsByCategoryId(category_id);

        return products.stream()
            .map(product -> createProductDetailDTO(product))
            .collect(Collectors.toList());
    }

    private ProductDetailDTO createProductDetailDTO(Product product) {
        ProductDetailDTO productDTO = new ProductDetailDTO();
        productDTO.setProductId(product.getProduct_id());
        productDTO.setBrand(product.getP_brand());
        productDTO.setName(product.getP_name());
        productDTO.setPrice(product.getP_price());
        productDTO.setStatus(product.getP_status());
        productDTO.setIsSale(product.getP_isSale());
        productDTO.setQuantity(product.getP_quantity());
        productDTO.setCategory_id(product.getCategory().getCategory_id());
        productDTO.setCategory_name(product.getCategory().getCategory_name());

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
        List<String> imageUrls = productImages.stream()
            .map(Product_image::getImageUrl)
            .collect(Collectors.toList());

        productDTO.setImageUrls(imageUrls);

        return productDTO;
    }
}
