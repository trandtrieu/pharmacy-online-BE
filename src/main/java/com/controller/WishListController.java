package com.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Account;
import com.model.Product;
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
	
	
	
	@PostMapping("/add-wishlist")
	public ResponseEntity<String> addToWishlist1(@RequestParam Long accountId, @RequestParam Integer productId) {
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

}
