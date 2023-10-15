package com.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Account;
import com.model.Cart;
import com.model.CartItem;
import com.model.Product;
import com.repository.AccountRepository;

@Service
public class WishListService {

	@Autowired
	private AccountRepository accountRepository;
	
	public int countProductsInWishlist(Long accountId) {
	    Account account = accountRepository.findById(accountId).orElse(null);

	    if (account != null && account.getCart() != null) {
	        Cart cart = account.getCart();
	        List<CartItem> cartItems = cart.getCartItems();

	        Set<Product> uniqueProducts = new HashSet<>();
	        
	        for (CartItem cartItem : cartItems) {
	            uniqueProducts.add(cartItem.getProduct());
	        }

	        return uniqueProducts.size();
	    } else {
	        return 0; 
	    }
	}
}
