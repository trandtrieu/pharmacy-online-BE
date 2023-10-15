package com.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.CartItemDTO;
import com.dto.ProductDetailDTO;
import com.model.Account;
import com.model.Cart;
import com.model.CartItem;
import com.model.Product;
import com.model.Product_image;
import com.repository.AccountRepository;
import com.repository.CartItemRepository;
import com.repository.CartRepository;
import com.repository.ProductRepository;

@Service
public class CartService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CartItemRepository cartItemRepository;

	public void addToCart(Long accountId, int productId, int quantity) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

		Cart cart = account.getCart();
		if (cart == null) {
			cart = new Cart();
			cart.setAccount(account);
		}
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

		boolean productExistsInCart = false;
		for (CartItem cartItem : cart.getCartItems()) {
			if (cartItem.getProduct().equals(product)) {
				int newQuantity = cartItem.getQuantity() + quantity;
				cartItem.setQuantity(newQuantity);
				productExistsInCart = true;
				break;
			}
		}

		if (!productExistsInCart) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setCart(cart);
			cart.getCartItems().add(cartItem);
		}

		cartRepository.save(cart);
	}

	public List<CartItemDTO> getCartItems(Long accountId) {
		Account account = accountRepository.findById(accountId).orElse(null);

		if (account != null && account.getCart() != null) {
			Cart cart = account.getCart();
			List<CartItem> cartItems = cart.getCartItems();

			List<CartItemDTO> productDTOs = new ArrayList<>();
			for (CartItem cartItem : cartItems) {
				Product product = cartItem.getProduct();
				ProductDetailDTO productDetailDTO = new ProductDetailDTO();
				productDetailDTO.setProductId(product.getProduct_id());
				productDetailDTO.setName(product.getP_name());
				productDetailDTO.setPrice(product.getP_price());
				productDetailDTO.setBrand(product.getP_brand());
				productDetailDTO.setCategory_id(product.getCategory().getCategory_id());
				productDetailDTO.setCategory_name(product.getCategory().getCategory_name());
				List<String> imageUrls = product.getImages().stream().map(Product_image::getImageUrl)
						.collect(Collectors.toList());
				productDetailDTO.setImageUrls(imageUrls);
				CartItemDTO productWithQuantity = new CartItemDTO(cartItem.getId(), productDetailDTO,
						cartItem.getQuantity());
				productDTOs.add(productWithQuantity);

			}

			return productDTOs;
		} else {
			return Collections.emptyList();
		}
	}

	public void removeFromCart(Integer cartId) {
		CartItem cartItem = cartItemRepository.findById(cartId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));

		// Xóa cartItem khỏi giỏ hàng
		cartItemRepository.delete(cartItem);
	}

	public void updateCartItems(List<CartItemDTO> updatedCartItems) {
		for (CartItemDTO updatedCartItem : updatedCartItems) {
			int cartItemId = updatedCartItem.getCartId();
			int newQuantity = updatedCartItem.getQuantity();

			Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

			if (cartItemOptional.isPresent()) {
				CartItem cartItem = cartItemOptional.get();
				cartItem.setQuantity(newQuantity);

				// Update the cart item in the database
				cartItemRepository.save(cartItem);
			} else {
				// Handle the case where the cart item with the provided ID is not found
				// You can throw an exception or handle it as needed
				throw new RuntimeException("Cart item with ID " + cartItemId + " not found.");
			}
		}
	}
	
	public int getTotalQuantityInCart(Long accountId) {
	    Account account = accountRepository.findById(accountId).orElse(null);

	    if (account != null && account.getCart() != null) {
	        Cart cart = account.getCart();
	        List<CartItem> cartItems = cart.getCartItems();

	        int totalQuantity = 0;
	        for (CartItem cartItem : cartItems) {
	            totalQuantity += cartItem.getQuantity();
	        }

	        return totalQuantity;
	    } else {
	        return 0; // Hoặc bạn có thể ném một ngoại lệ nếu không tìm thấy tài khoản hoặc giỏ hàng.
	    }
	}
	public int countProductsInCart(Long accountId) {
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
