package com.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<String> addToCartByAdmin(@RequestParam Long accountId, @RequestParam int productId,
			@RequestParam int quantity, @RequestParam int cart_type) {
		try {
			cartService.addToCart(accountId, productId, quantity, cart_type);
			return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Lỗi khi thêm sản phẩm vào giỏ hàng: " + e.getMessage());
		}
	}

	@GetMapping("/get-cart")
	public ResponseEntity<List<CartItemDTO>> getProductsInCart(@RequestParam Long accountId,
			@RequestParam int cart_type) {
		List<CartItemDTO> cartItems = cartService.getCartItems(accountId, cart_type);

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

	@PostMapping("/update-cart-items")
	public ResponseEntity<String> updateCartItemsToCartType2(@RequestParam Long accountId, @RequestParam int cartType) {
		try {
			cartService.updateCartItemsToCartType2(accountId, cartType);
			return new ResponseEntity<>("Các cart item đã được cập nhật thành cart type 2", HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Lỗi khi cập nhật cart type: " + e.getMessage());
		}
	}

	@GetMapping("/get-total-quantity-in-cart")
	public ResponseEntity<Integer> getTotalQuantityInCart(@RequestParam Long accountId, @RequestParam int cartType) {
		int totalQuantity = cartService.getTotalQuantityInCart(accountId, cartType);
		return ResponseEntity.ok(totalQuantity);
	}

	@GetMapping("/count-product-cart")
	public ResponseEntity<Integer> countProductsInCart(@RequestParam Long accountId, @RequestParam int cartType) {
		int uniqueProductCount = cartService.countProductsInCart(accountId, cartType);
		return ResponseEntity.ok(uniqueProductCount);
	}
}
