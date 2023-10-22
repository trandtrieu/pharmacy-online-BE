package com.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.ProductDetailDTO;
import com.model.Category;
import com.model.Product;
import com.model.Product_detail;
import com.model.Product_image;
import com.repository.CategoryRepository;
import com.repository.ProductDetailRepository;
import com.repository.ProductImageRepository;
import com.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	private final ProductDetailRepository productDetailRepository;
	private final ProductImageRepository productImageRepository;
	private final CategoryRepository categoryRepository;

	@Autowired
	public ProductService(ProductRepository productRepository, ProductDetailRepository productDetailRepository,
			ProductImageRepository productImageRepository, CategoryRepository categoryRepository) {
		super();
		this.productRepository = productRepository;
		this.productDetailRepository = productDetailRepository;
		this.productImageRepository = productImageRepository;
		this.categoryRepository = categoryRepository;
	}

	@Transactional
	public void addProduct(ProductDetailDTO productDetailDTO) {
		// Tạo một đối tượng Product từ DTO
		Product product = new Product();
		product.setP_name(productDetailDTO.getName());
		product.setP_price(productDetailDTO.getPrice());
		product.setP_brand(productDetailDTO.getBrand());
		product.setP_status(productDetailDTO.getStatus());
		// Lưu Product vào cơ sở dữ liệu
		

		Category category = categoryRepository.findById(productDetailDTO.getCategory_id()).orElse(null);

		// Kiểm tra xem category có tồn tại không
		if (category != null) {
			// Gán category cho sản phẩm
			product.setCategory(category);
		}
		
		productRepository.save(product);

		// Tạo một đối tượng Product_detail từ DTO
		Product_detail productDetail = new Product_detail();
		productDetail.setP_component(productDetailDTO.getComponent());
		productDetail.setP_vitue(productDetailDTO.getVitue());
		productDetail.setP_object(productDetailDTO.getObject());
		productDetail.setP_guide(productDetailDTO.getGuide());
		productDetail.setP_preservation(productDetailDTO.getPreservation());
		productDetail.setP_instruction(productDetailDTO.getInstruction());
		productDetail.setP_store(productDetailDTO.getStore());
		productDetail.setP_madeIn(productDetailDTO.getMadeIn());
		productDetail.setProduct(product);

		// Lưu Product_detail vào cơ sở dữ liệu
		productDetailRepository.save(productDetail);

		// Lưu danh sách hình ảnh sản phẩm
		List<String> imageUrls = productDetailDTO.getImageUrls();
		if (imageUrls != null && !imageUrls.isEmpty()) {
			List<Product_image> productImages = new ArrayList<>();
			for (String imageUrl : imageUrls) {
				Product_image productImage = new Product_image();
				productImage.setImageUrl(imageUrl);
				productImage.setProduct(product);
				productImages.add(productImage);
			}
			productImageRepository.saveAll(productImages);
		}
	}
	
	
	@Transactional
	public void updateProduct(Integer productId, ProductDetailDTO productDetailDTO) {
	    // Kiểm tra xem sản phẩm có tồn tại không
	    Optional<Product> productOptional = productRepository.findById(productId);
	    
	    if (productOptional.isPresent()) {
	        Product product = productOptional.get();
	        
	        // Cập nhật thông tin sản phẩm từ DTO
	        product.setP_name(productDetailDTO.getName());
	        product.setP_price(productDetailDTO.getPrice());
	        product.setP_brand(productDetailDTO.getBrand());
	        product.setP_status(productDetailDTO.getStatus());

	        Category category = categoryRepository.findById(productDetailDTO.getCategory_id()).orElse(null);
	        // Kiểm tra xem category có tồn tại không
	        if (category != null) {
	        	category.setCategory_id(productDetailDTO.getCategory_id());
	            product.setCategory(category);
	        }
	        productRepository.save(product);

	        // Tìm chi tiết sản phẩm dựa trên productId
	        Product_detail productDetail = productDetailRepository.findByProduct(product);
	        if (productDetail!= null) {
	            // Cập nhật thông tin chi tiết sản phẩm từ DTO
	            productDetail.setP_component(productDetailDTO.getComponent());
	            productDetail.setP_vitue(productDetailDTO.getVitue());
	            productDetail.setP_object(productDetailDTO.getObject());
	            productDetail.setP_guide(productDetailDTO.getGuide());
	            productDetail.setP_preservation(productDetailDTO.getPreservation());
	            productDetail.setP_instruction(productDetailDTO.getInstruction());
	            productDetail.setP_store(productDetailDTO.getStore());
	            productDetail.setP_madeIn(productDetailDTO.getMadeIn());

	            productDetailRepository.save(productDetail);
	        }

	        // Lưu danh sách hình ảnh sản phẩm
	        List<String> imageUrls = productDetailDTO.getImageUrls();
	        if (imageUrls != null && !imageUrls.isEmpty()) {
	            List<Product_image> productImages = new ArrayList<>();
	            
	            for (String imageUrl : imageUrls) {
	                Product_image productImage = new Product_image();
	                productImage.setImageUrl(imageUrl);
	                productImage.setProduct(product);
	                productImages.add(productImage);
	            }
	            productImageRepository.saveAll(productImages);
	        }
	    }
	}


}
