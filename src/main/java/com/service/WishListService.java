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
	        WishList wishList = account.getWishList();
	        if (wishList == null) {
	            wishList = new WishList();
	            wishList.setAccount(account);
	            wishList.setCreatedDate(new Date());
	            account.setWishList(wishList);
	        }

	        // Kiểm tra xem sản phẩm đã tồn tại trong danh sách sản phẩm của wishlist hay chưa
	        boolean productAlreadyInWishlist = wishList.getProducts().contains(product);

	        if (!productAlreadyInWishlist) {
	            wishList.addProduct(product);
	            accountRepository.save(account);
	        }
	        // Không cần xử lý nếu sản phẩm đã tồn tại trong danh sách
	    }
	}

}
