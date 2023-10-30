package com.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ProductFilterController {
	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Autowired
	private ProductRepository productRepository;

//	@Autowired
//	private ProductService productService;

	@Autowired
	private ProductImageRepository productImageRepository;

	private List<ProductDetailDTO> filterProductsInRange(List<ProductDetailDTO> products, BigDecimal minPrice,
			BigDecimal maxPrice) {
		List<ProductDetailDTO> filteredProducts = new ArrayList<>();
		for (ProductDetailDTO product : products) {
			BigDecimal price = product.getPrice();
			if (price.compareTo(minPrice) > 0 && price.compareTo(maxPrice) <= 0) {
				filteredProducts.add(product);
			}
		}
		return filteredProducts;
	}

	private List<ProductDetailDTO> filterProductsGreaterThan(List<ProductDetailDTO> products, BigDecimal minPrice) {
		List<ProductDetailDTO> filteredProducts = new ArrayList<>();
		for (ProductDetailDTO product : products) {
			BigDecimal price = product.getPrice();
			if (price.compareTo(minPrice) > 0) {
				filteredProducts.add(product);
			}
		}
		return filteredProducts;
	}

	@GetMapping("/products/searchKeyword")
	public ResponseEntity<List<ProductDetailDTO>> searchProductByKeyword(@RequestParam("keyword") String keyword) {
		List<ProductDetailDTO> matchingProducts = new ArrayList<>();

		List<Product> products = productRepository.findAll();

		for (Product product : products) {
			String productName = product.getP_name();
			String productBrand = product.getP_brand();

			if (productName.toLowerCase().contains(keyword.toLowerCase())
					|| productBrand.toLowerCase().contains(keyword.toLowerCase())) {
				matchingProducts.add(createProductDetailDTO(product));
			}
		}
		return !matchingProducts.isEmpty() ? ResponseEntity.ok(matchingProducts) : ResponseEntity.notFound().build();
	}

//	   @GetMapping("/products/countByRange")	
//	    public ResponseEntity<Long> countProductsInPriceRange(
//	            @RequestParam("minPrice") BigDecimal minPrice,
//	            @RequestParam("maxPrice") BigDecimal maxPrice) {
//	        Long count = productService.countByRange(minPrice, maxPrice);
//	        return ResponseEntity.ok(count);
//	    }
//	
	@GetMapping("/products/searchKeywordAndFilterPrice")
	public ResponseEntity<List<ProductDetailDTO>> searchProductByKeyword(@RequestParam("keyword") String keyword,
			@RequestParam("priceFilter") String priceFilter) {
		List<ProductDetailDTO> matchingProducts = new ArrayList<>();

		List<Product> products = productRepository.findAll();

		for (Product product : products) {
			String productName = product.getP_name();
			String productBrand = product.getP_brand();

			if (productName.toLowerCase().contains(keyword.toLowerCase())
					|| productBrand.toLowerCase().contains(keyword.toLowerCase())) {
				matchingProducts.add(createProductDetailDTO(product));
			}
		}

		List<ProductDetailDTO> filteredProducts = new ArrayList<>();

		if (!matchingProducts.isEmpty()) {
			switch (priceFilter) {
			case "price-all":
				// Không thực hiện lọc theo giá

				filteredProducts = matchingProducts;
				break;
			case "price-1":
				filteredProducts = filterProductsInRange(matchingProducts, BigDecimal.valueOf(0),
						BigDecimal.valueOf(100));
				break;
			case "price-2":
				filteredProducts = filterProductsInRange(matchingProducts, BigDecimal.valueOf(100),
						BigDecimal.valueOf(200));
				break;
			case "price-3":
				filteredProducts = filterProductsInRange(matchingProducts, BigDecimal.valueOf(200),
						BigDecimal.valueOf(500));
				break;
			case "price-4":
				filteredProducts = filterProductsInRange(matchingProducts, BigDecimal.valueOf(500),
						BigDecimal.valueOf(1000));
				break;
			case "price-5":
				filteredProducts = filterProductsGreaterThan(matchingProducts, BigDecimal.valueOf(1000));
				break;
			}
		}
		return !filteredProducts.isEmpty() ? ResponseEntity.ok(filteredProducts) : ResponseEntity.notFound().build();
	}

	@GetMapping("/products/filter/alphaAsc")
	public ResponseEntity<List<ProductDetailDTO>> filterProductAZ() {

		List<Product> products = productRepository.findAllSortedByNameAsc();
		List<ProductDetailDTO> sortedProducts = new ArrayList<>();

		for (Product product : products) {
			sortedProducts.add(createProductDetailDTO(product));
		}
		return !sortedProducts.isEmpty() ? ResponseEntity.ok(sortedProducts) : ResponseEntity.notFound().build();

	}

	@GetMapping("products/filter/alphaDesc")
	public ResponseEntity<List<ProductDetailDTO>> filterProductZA() {
		List<Product> products = productRepository.findAllSortedByNameDesc();
		List<ProductDetailDTO> sortedProducts = new ArrayList<>();
		for (Product product : products) {
			sortedProducts.add(createProductDetailDTO(product));
		}
		return !sortedProducts.isEmpty() ? ResponseEntity.ok(sortedProducts) : ResponseEntity.notFound().build();
	}

	@GetMapping("products/filter/priceAsc")
	public ResponseEntity<List<ProductDetailDTO>> filterPriceAsc() {
		List<Product> products = productRepository.findAllSortedByPriceAsc();
		List<ProductDetailDTO> sortedProducts = new ArrayList<>();
		for (Product product : products) {
			sortedProducts.add(createProductDetailDTO(product));
		}
		return !sortedProducts.isEmpty() ? ResponseEntity.ok(sortedProducts) : ResponseEntity.notFound().build();
	}

	@GetMapping("products/filter/priceDesc")
	public ResponseEntity<List<ProductDetailDTO>> filterPriceDesc() {
		List<Product> products = productRepository.findAllSortedByPriceDesc();
		List<ProductDetailDTO> sortedProducts = new ArrayList<>();
		for (Product product : products) {
			sortedProducts.add(createProductDetailDTO(product));
		}
		return !sortedProducts.isEmpty() ? ResponseEntity.ok(sortedProducts) : ResponseEntity.notFound().build();
	}

	@GetMapping("products/filter/allPrice")
	public ResponseEntity<List<ProductDetailDTO>> filterAllPrice() {
		List<Product> products = productRepository.findAllWithAllPrice();
		List<ProductDetailDTO> sortedProducts = new ArrayList<>();
		for (Product product : products) {
			sortedProducts.add(createProductDetailDTO(product));
		}
		return !sortedProducts.isEmpty() ? ResponseEntity.ok(sortedProducts) : ResponseEntity.notFound().build();
	}

	@GetMapping("products/filter/rangefilt01")
	public ResponseEntity<List<ProductDetailDTO>> findAllWithRange0To100() {
		List<Product> products = productRepository.findAllWithRange0To100();
		List<ProductDetailDTO> sortedProducts = new ArrayList<>();
		for (Product product : products) {
			sortedProducts.add(createProductDetailDTO(product));
		}
		return !sortedProducts.isEmpty() ? ResponseEntity.ok(sortedProducts) : ResponseEntity.notFound().build();
	}

	@GetMapping("products/filter/rangefilt02")
	public ResponseEntity<List<ProductDetailDTO>> findAllWithRange100To200() {
		List<Product> products = productRepository.findAllWithRange100To200();
		List<ProductDetailDTO> sortedProducts = new ArrayList<>();
		for (Product product : products) {
			sortedProducts.add(createProductDetailDTO(product));
		}
		return !sortedProducts.isEmpty() ? ResponseEntity.ok(sortedProducts) : ResponseEntity.notFound().build();
	}

	@GetMapping("products/filter/rangefilt03")
	public ResponseEntity<List<ProductDetailDTO>> findAllWithRange200To500() {
		List<Product> products = productRepository.findAllWithRange200To500();
		List<ProductDetailDTO> sortedProducts = new ArrayList<>();
		for (Product product : products) {
			sortedProducts.add(createProductDetailDTO(product));
		}
		return !sortedProducts.isEmpty() ? ResponseEntity.ok(sortedProducts) : ResponseEntity.notFound().build();
	}

	@GetMapping("products/filter/rangefilt04")
	public ResponseEntity<List<ProductDetailDTO>> findAllWithRange500To1000() {
		List<Product> products = productRepository.findAllWithRange500To1000();
		List<ProductDetailDTO> sortedProducts = new ArrayList<>();
		for (Product product : products) {
			sortedProducts.add(createProductDetailDTO(product));
		}
		return !sortedProducts.isEmpty() ? ResponseEntity.ok(sortedProducts) : ResponseEntity.notFound().build();
	}

	@GetMapping("products/filter/rangefilt05")
	public ResponseEntity<List<ProductDetailDTO>> findAllWithRangeGreaterThan1000() {
		List<Product> products = productRepository.findAllWithRangeGreaterThan1000();
		List<ProductDetailDTO> sortedProducts = new ArrayList<>();
		for (Product product : products) {
			sortedProducts.add(createProductDetailDTO(product));
		}
		return !sortedProducts.isEmpty() ? ResponseEntity.ok(sortedProducts) : ResponseEntity.notFound().build();
	}

	@GetMapping("products/search/latest")
	public ResponseEntity<List<ProductDetailDTO>> findProductsLatest() {
		List<Product> products = productRepository.findProductsLatest();
		List<ProductDetailDTO> sortedProducts = new ArrayList<>();
		for (Product product : products) {
			sortedProducts.add(createProductDetailDTO(product));
		}
		return !sortedProducts.isEmpty() ? ResponseEntity.ok(sortedProducts) : ResponseEntity.notFound().build();
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
