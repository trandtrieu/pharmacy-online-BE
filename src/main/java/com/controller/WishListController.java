package com.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ProductDetailDTO;
import com.model.Account;
import com.model.Product;
import com.model.Product_image;
import com.model.WishList;
import com.repository.AccountRepository;
import com.repository.ProductRepository;
import com.repository.WishListRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class WishListController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private WishListRepository wishListRepository;

	@PostMapping("/wishlist/add-wishlist")
	public ResponseEntity<String> addToWishlist(@RequestParam Long accountId, @RequestParam Integer productId) {
		Account account = accountRepository.findById(accountId).orElse(null);
		Product product = productRepository.findById(productId).orElse(null);

		if (account != null && product != null) {
			if (account.getWishList() == null) {
				account.setWishList(new WishList());
			}
			WishList wishList = account.getWishList();
			wishList.addProduct(product);
			wishList.setAccount(account);
			wishList.setCreatedDate(new Date());
			accountRepository.save(account);
			return ResponseEntity.ok("Product added to wishlist successfully.");
		} else {
			return ResponseEntity.badRequest().body("Failed to add product to wishlist.");
		}
	}

	@GetMapping("/wishlist/id/{accountId}")
	public ResponseEntity<Set<Integer>> getWishlistByAccountId(@PathVariable Long accountId) {
		Account account = accountRepository.findById(accountId).orElse(null);

		if (account != null && account.getWishList() != null) {
			WishList wishlist = account.getWishList();

			Set<Product> products = wishlist.getProducts();
			Set<Integer> productIds = products.stream().map(Product::getProduct_id).collect(Collectors.toSet());
			return ResponseEntity.ok(productIds);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/wishlist/{accountId}")
	public ResponseEntity<Set<ProductDetailDTO>> getProductsInWishlist(@PathVariable Long accountId) {
		Account account = accountRepository.findById(accountId).orElse(null);

		if (account != null && account.getWishList() != null) {
			WishList wishlist = account.getWishList();
			Set<Product> products = wishlist.getProducts();

			Set<ProductDetailDTO> productDTOs = products.stream().map(product -> {
				ProductDetailDTO productDetailDTO = new ProductDetailDTO();
				productDetailDTO.setProductId(product.getProduct_id());
				productDetailDTO.setName(product.getP_name());
				productDetailDTO.setPrice(product.getP_price());

				List<String> imageUrls = product.getImages().stream().map(Product_image::getImageUrl)
						.collect(Collectors.toList());

				productDetailDTO.setImageUrls(imageUrls);

				return productDetailDTO;
			}).collect(Collectors.toSet());

			return ResponseEntity.ok(productDTOs);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/wishlist/{accountId}/remove-product/{productId}")
	public ResponseEntity<String> removeProductFromWishlist(@PathVariable Long accountId,
			@PathVariable Integer productId) {
		Account account = accountRepository.findById(accountId).orElse(null);

		if (account != null && account.getWishList() != null) {
			WishList wishlist = account.getWishList();

			Product productToRemove = null;
			for (Product product : wishlist.getProducts()) {
			    if (product.getProduct_id() == productId) {
					productToRemove = product;
					break;
				}
			}
			if (productToRemove != null) {
				wishlist.getProducts().remove(productToRemove);
				wishListRepository.save(wishlist);
				return ResponseEntity.ok("Product removed from wishlist successfully.");
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
