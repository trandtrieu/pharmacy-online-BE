package com.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CartItemDTO;
import com.service.CartService;

@CrossOrigin(origins = "http://localhost:3006")
@RestController
@RequestMapping("/pharmacy-online/admin/cart/")
public class CartAdminController {
	@Autowired
	private CartService cartService;

	@PostMapping("/add-cart")
	public ResponseEntity<String> addToCart(@RequestParam Long accountId, @RequestParam int productId,
			@RequestParam int quantity) {
		try {
			cartService.addToCart(accountId, productId, quantity);
			return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Lỗi khi thêm sản phẩm vào giỏ hàng: " + e.getMessage());
		}
	}

	@GetMapping("/get-cart")
	public ResponseEntity<List<CartItemDTO>> getProductsInCart(@RequestParam Long accountId) {
		List<CartItemDTO> cartItems = cartService.getCartItems(accountId);
		if (cartItems.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(cartItems);
		}
	}

	@DeleteMapping("/remove-from-cart")
	public ResponseEntity<String> removeFromCart(@RequestParam Integer cartId) {
	    try {
	        cartService.removeFromCart(cartId);
	        return ResponseEntity.ok("Sản phẩm đã được xóa khỏi giỏ hàng.");
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Lỗi khi xóa sản phẩm khỏi giỏ hàng: " + e.getMessage());
	    }
	}
	
	
	 @PutMapping("/update-cart")
	  public ResponseEntity<String> updateCart(@RequestBody List<CartItemDTO> updatedCartItems) {
	    try {
	      cartService.updateCartItems(updatedCartItems);
	      return ResponseEntity.ok("Cart updated successfully.");
	    } catch (Exception e) {
	      return ResponseEntity.badRequest().body("Error updating cart: " + e.getMessage());
	    }
	  }
	 
	 @GetMapping("/get-total-quantity-in-cart")
	 public ResponseEntity<Integer> getTotalQuantityInCart(@RequestParam Long accountId) {
	     int totalQuantity = cartService.getTotalQuantityInCart(accountId);
	     return ResponseEntity.ok(totalQuantity);
	 }
	 
	 @GetMapping("/count-product-cart")
	 public ResponseEntity<Integer> countProductsInCart(@RequestParam Long accountId) {
	     int uniqueProductCount = cartService.countProductsInCart(accountId);
	     return ResponseEntity.ok(uniqueProductCount);
	 }

}

