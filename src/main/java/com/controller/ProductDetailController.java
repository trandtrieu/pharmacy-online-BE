package com.controller;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    

    @GetMapping("/products/count")
    public int countProductsByCategoryId(@RequestParam("category_id") Long category_id) {
        List<Product> products = productRepository.findProductsByCategoryId(category_id);
        int productCount = products.size();

        return productCount;
    }

    
    
    @GetMapping("/products/random")
    public List<ProductDetailDTO> getRandomProducts() {
        List<Product> products = productRepository.findAll();
        if (products.size() <= 4) {
            return products.stream()
                .map(product -> createProductDetailDTO(product))
                .collect(Collectors.toList());
        }

        Random random = new Random();
        Collections.shuffle(products, random);

        List<Product> randomProducts = products.subList(0, 4);

        return randomProducts.stream()
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
        productDTO.setCreatedDate(product.getCreatedDate());
        productDTO.setIsSale(product.getP_isSale());
        productDTO.setQuantity(product.getP_quantity());
        productDTO.setCategory_id(product.getCategory().getCategory_id());
        productDTO.setCategory_name(product.getCategory().getCategory_name());

        Product_detail productDetail = productDetailRepository.findByProduct(product);
        if (productDetail != null) {
            productDTO.setIngredients(productDetail.getP_Ingredients());
            productDTO.setIndications(productDetail.getP_Indications());
            productDTO.setContraindications(productDetail.getP_Contraindications());
            productDTO.setDosageAndUsage(productDetail.getP_DosageAndUsage());
            productDTO.setSideEffects(productDetail.getP_SideEffects());
            productDTO.setPrecautions(productDetail.getP_Precautions());
            productDTO.setDrugInteractions(productDetail.getP_DrugInteractions());;
            productDTO.setStorage(productDetail.getP_Storage());
            productDTO.setPackaging(productDetail.getP_Packaging());
            productDTO.setMadeIn(productDetail.getP_madeIn());
        }

        List<Product_image> productImages = productImageRepository.findByProduct(product);
        List<String> imageUrls = productImages.stream()
            .map(Product_image::getImageUrl)
            .collect(Collectors.toList());

        productDTO.setImageUrls(imageUrls);

        return productDTO;
    }
}
