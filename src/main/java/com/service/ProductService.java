package com.service;

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
	@Autowired
	private ProductRepository productRepository;
	@Autowired

	private ProductDetailRepository productDetailRepository;
	@Autowired
	private ProductImageRepository productImageRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional
	public void addProduct(ProductDetailDTO productDetailDTO) {
		// Tạo một đối tượng Product từ DTO
		Product product = new Product();
		product.setP_name(productDetailDTO.getName());
		product.setP_price(productDetailDTO.getPrice());
		product.setP_brand(productDetailDTO.getBrand());
		product.setP_status(productDetailDTO.getStatus());
	
		Category category = categoryRepository.findById(productDetailDTO.getCategory_id()).orElse(null);
		if (category != null) {
			// Gán category cho sản phẩm
			product.setCategory(category);
		}
		productRepository.save(product);

		// Tạo một đối tượng Product_detail từ DTO
		Product_detail productDetail = new Product_detail();
		productDetail.setP_Contraindications(productDetailDTO.getContraindications());
		productDetail.setP_DosageAndUsage(productDetailDTO.getDosageAndUsage());
		productDetail.setP_DrugInteractions(productDetailDTO.getDrugInteractions());
		productDetail.setP_Indications(productDetailDTO.getIndications());
		productDetail.setP_Ingredients(productDetailDTO.getIngredients());
		productDetail.setP_Packaging(productDetailDTO.getPackaging());
		productDetail.setP_Precautions(productDetailDTO.getPrecautions());
		productDetail.setP_SideEffects(productDetailDTO.getSideEffects());
		productDetail.setP_Storage(productDetailDTO.getStorage());
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
			product.setP_quantity(productDetailDTO.getQuantity());
			if(product.getP_quantity()==0) {
				product.setP_status(0);
			}else {
				product.setP_status(1);
			}
//			product.setP_status(productDetailDTO.getStatus());
			product.setP_type(productDetailDTO.getType());
			Category category = categoryRepository.findById(productDetailDTO.getCategory_id()).orElse(null);
			// Kiểm tra xem category có tồn tại không
			if (category != null) {
				category.setCategory_id(productDetailDTO.getCategory_id());
				product.setCategory(category);
			}
			productRepository.save(product);
			Product_detail productDetail = productDetailRepository.findByProduct(product);
			if (productDetail != null) {
				productDetail.setP_Contraindications(productDetailDTO.getContraindications());
				productDetail.setP_DosageAndUsage(productDetailDTO.getDosageAndUsage());
				productDetail.setP_DrugInteractions(productDetailDTO.getDrugInteractions());
				productDetail.setP_Indications(productDetailDTO.getIndications());
				productDetail.setP_Ingredients(productDetailDTO.getIngredients());
				productDetail.setP_Packaging(productDetailDTO.getPackaging());
				productDetail.setP_Precautions(productDetailDTO.getPrecautions());
				productDetail.setP_SideEffects(productDetailDTO.getSideEffects());
				productDetail.setP_Storage(productDetailDTO.getStorage());
				productDetail.setP_madeIn(productDetailDTO.getMadeIn());

				productDetailRepository.save(productDetail);
			}

			// Lưu danh sách hình ảnh sản phẩm
//			List<String> imageUrls = productDetailDTO.getImageUrls();
	        // Lấy danh sách hình ảnh sản phẩm cũ
	        List<Product_image> oldImages = productImageRepository.findByProduct(product);

	        // Lấy danh sách hình ảnh mới từ DTO
	        List<String> newImageUrls = productDetailDTO.getImageUrls();

	        List<Product_image> productImages = new ArrayList<>();
	        if (newImageUrls != null && !newImageUrls.isEmpty()) {
	            // Xóa các ảnh cũ
	            productImageRepository.deleteAll(oldImages);

	            // Thêm các ảnh mới
	            for (String imageUrl : newImageUrls) {
	                Product_image productImage = new Product_image();
	                productImage.setImageUrl(imageUrl);
	                productImage.setProduct(product);
	                productImages.add(productImage);
	            }
	            productImageRepository.saveAll(productImages);
	        } else {
	            // Nếu không có ảnh mới, giữ nguyên các ảnh cũ
	            productImages.addAll(oldImages);
	        }
		}
	}


	public Product getProductById(Integer productId) {
		return productRepository.findById(productId).orElse(null);
	}

}
