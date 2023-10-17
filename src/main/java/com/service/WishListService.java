package com.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Account;
import com.model.Product;
import com.model.WishList;
import com.repository.AccountRepository;
import com.repository.ProductRepository;

@Service
public class WishListService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ProductRepository productRepository;

	public void addToWishlist(Long accountId, int productId) {
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
		}
	}
	
	
	
}
