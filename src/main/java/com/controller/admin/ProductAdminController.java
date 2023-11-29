package com.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ProductDetailDTO;
import com.model.CartItem;
import com.model.Product;
import com.model.Product_detail;
import com.model.Product_image;
import com.model.WishList;
import com.repository.CartItemRepository;
import com.repository.ProductDetailRepository;
import com.repository.ProductImageRepository;
import com.repository.ProductRepository;
import com.repository.WishListRepository;
import com.service.ProductService;

@CrossOrigin(origins = "http://localhost:3006")
@RestController
@RequestMapping("/pharmacy-online/admin/products")
public class ProductAdminController {
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductImageRepository productImageRepository;
	
	@Autowired
	private WishListRepository wishListRepositoy;
	
	@Autowired
	private CartItemRepository cartItemRepository;

	@GetMapping("/search-list-products")
	public List<ProductDetailDTO> getAllProductsWithDetailsAndImages() {
		List<Product> products = productRepository.findAll();

		return products.stream().map(product -> createProductDetailDTO(product)).collect(Collectors.toList());
	}
	//do paging 
	@GetMapping("/list-products")
	public Page<ProductDetailDTO> getAllProductsWithDetailsAndImages(Pageable pageable) {
	    Page<Product> productsPage = productRepository.findAll(pageable);

	    return productsPage.map(this::createProductDetailDTO);
	}
	
	@GetMapping("/list")
	public List<ProductDetailDTO> getAllProducts() {
		List<Product> products = productRepository.findAll();

		return products.stream().map(product -> createProductDetailDTO(product)).collect(Collectors.toList());
	}
	@PostMapping("/add-product")
	public ResponseEntity<String> addProduct(@RequestBody ProductDetailDTO productDetailDTO) {
		productService.addProduct(productDetailDTO);
		return ResponseEntity.ok("Sản phẩm đã được thêm thành công.");
	}

	@PutMapping("/update-product/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody ProductDetailDTO productDetails) {
		productService.updateProduct(id, productDetails);

		return ResponseEntity.ok("Sản phẩm đã được cập nhập thành công.");

	}
	
	
	@DeleteMapping("/delete-product/{productId}")
		public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
			try {
				Product product = productRepository.findById(productId).orElse(null);

				if (product == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm.");
				}

				Product_detail productDetail = productDetailRepository.findByProduct(product);
				List<Product_image> productImages = productImageRepository.findByProduct(product);
				CartItem cartItem = cartItemRepository.findByProduct(product);
				Set<WishList> wishLists = wishListRepositoy.findByProducts(product);

				// Remove product from wishlists
				if (wishLists != null) {
					for (WishList wishList : wishLists) {
						wishList.removeProduct(product);
					}
					wishListRepositoy.saveAll(wishLists);
				}

				// Remove product from cart if it exists
				if (cartItem != null) {
					cartItemRepository.deleteById(cartItem.getId());
				}

				// Remove product images
				productImageRepository.deleteAll(productImages);

				// Remove product details
				if (productDetail != null) {
					productDetailRepository.deleteById(productDetail.getDetail_id());
				}

				// Delete the product
				productRepository.deleteById(productId);

				return ResponseEntity.ok("Sản phẩm đã được xóa thành công.");
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xóa sản phẩm không thành công.");
			}
		}
	
	
	//get product by id
	@GetMapping("/{productId}")
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
		productDTO.setCategory_name(product.getCategory().getCategory_name());
		productDTO.setQuantity(product.getP_quantity());
		productDTO.setType(product.getP_type());
		productDTO.setCreatedDate(product.getCreatedDate());

		Product_detail productDetail = productDetailRepository.findByProduct(product);
		if (productDetail != null) {
			productDTO.setIngredients(productDetail.getP_Ingredients());
			productDTO.setIndications(productDetail.getP_Indications());
			productDTO.setContraindications(productDetail.getP_Contraindications());
			productDTO.setMadeIn(productDetail.getP_madeIn());
			productDTO.setDosageAndUsage(productDetail.getP_DosageAndUsage());
			productDTO.setSideEffects(productDetail.getP_SideEffects());
			productDTO.setPrecautions(productDetail.getP_Precautions());
			productDTO.setDrugInteractions(productDetail.getP_DrugInteractions());
			productDTO.setStorage(productDetail.getP_Storage());
			productDTO.setPackaging(productDetail.getP_Packaging());
		}

		List<Product_image> productImages = productImageRepository.findByProduct(product);
		List<String> imageUrls = new ArrayList<>();
		for (Product_image productImage : productImages) {
			imageUrls.add(productImage.getImageUrl());
		}
		productDTO.setImageUrls(imageUrls);

		return ResponseEntity.ok(productDTO);
	}

	private ProductDetailDTO createProductDetailDTO(Product product) {
		ProductDetailDTO productDTO = new ProductDetailDTO();
		productDTO.setProductId(product.getProduct_id());
		productDTO.setBrand(product.getP_brand());
		productDTO.setName(product.getP_name());
		productDTO.setPrice(product.getP_price());
		productDTO.setStatus(product.getP_status());
		productDTO.setCreatedDate(product.getCreatedDate());
		productDTO.setType(product.getP_type());
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
			productDTO.setDrugInteractions(productDetail.getP_DrugInteractions());
			;
			productDTO.setStorage(productDetail.getP_Storage());
			productDTO.setPackaging(productDetail.getP_Packaging());
			productDTO.setMadeIn(productDetail.getP_madeIn());
		}

		List<Product_image> productImages = productImageRepository.findByProduct(product);
		List<String> imageUrls = productImages.stream().map(Product_image::getImageUrl).collect(Collectors.toList());

		productDTO.setImageUrls(imageUrls);

		return productDTO;
	}
	
	
}