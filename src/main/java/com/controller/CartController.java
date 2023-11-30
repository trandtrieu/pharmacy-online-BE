package com.controller;

import java.math.BigDecimal;
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
import com.dto.DiscountCalculationResultDTO;
import com.model.DiscountCode;
import com.service.CartService;
import com.service.DiscountCodeService;

//@CrossOrigin(origins = "*")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/pharmacy-online/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	@Autowired
	private DiscountCodeService discountCodeService;

	@PostMapping("/add-cart")
	public ResponseEntity<String> addToCart(@RequestParam Long accountId, @RequestParam int productId,
			@RequestParam int quantity, @RequestParam int cart_type) {
		try {
			cartService.addToCart(accountId, productId, quantity, cart_type);
			// return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng.");
			return new ResponseEntity<>("Hello World!", HttpStatus.OK);
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

	@DeleteMapping("/clear-cart")
	public ResponseEntity<String> clearCartByAccountAndType(@RequestParam Long accountId, @RequestParam int cartType) {
		cartService.clearCartByAccountAndType(accountId, cartType);
		return ResponseEntity.ok("Cart of type " + cartType + " for account " + accountId + " has been cleared.");
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
	public ResponseEntity<Integer> getTotalQuantityInCart(@RequestParam Long accountId, @RequestParam int cartType) {
		int totalQuantity = cartService.getTotalQuantityInCart(accountId, cartType);
		return ResponseEntity.ok(totalQuantity);
	}

	@GetMapping("/count-product-cart")
	public ResponseEntity<Integer> countProductsInCart(@RequestParam Long accountId, @RequestParam int cartType) {
		int uniqueProductCount = cartService.countProductsInCart(accountId, cartType);
		return ResponseEntity.ok(uniqueProductCount);
	}

	@GetMapping("/get-total-cart-cost")
	public ResponseEntity<BigDecimal> getTotalCartCost(@RequestParam Long accountId, @RequestParam int cartType) {
		BigDecimal totalCartCost = cartService.getTotalCostByAccountAndType(accountId, cartType);
		return ResponseEntity.ok(totalCartCost);
	}

	@GetMapping("/get-total-cart-cost-with-shipping")
	public ResponseEntity<BigDecimal> getTotalCartCostWithShipping(@RequestParam Long accountId,
			@RequestParam int cartType) {
		BigDecimal totalCartCost = cartService.getTotalCostByAccountAndTypeWithShipping(accountId, cartType);
		return ResponseEntity.ok(totalCartCost);
	}
	
	@GetMapping("/get-total-cart-cost-with-shipping-by-pharmacy")
	public ResponseEntity<BigDecimal> getTotalCartCostWithShippingByDelivery(@RequestParam Long accountId,
			@RequestParam int cartType) {
		BigDecimal totalCartCost = cartService.getTotalCostByAccountAndTypeShippingByPharmacy(accountId, cartType);
		return ResponseEntity.ok(totalCartCost);
	}

	@GetMapping("/get-shipping-cost")
	public ResponseEntity<BigDecimal> getShippingCost(@RequestParam Long accountId, @RequestParam int cartType) {
		BigDecimal totalCartCost = cartService.getTotalCostByAccountAndType(accountId, cartType);
		BigDecimal shippingCost;

		if (totalCartCost.compareTo(new BigDecimal(300000)) >= 0) {
			shippingCost = BigDecimal.ZERO;
		} else {
			shippingCost = new BigDecimal(30000);
		}
		return ResponseEntity.ok(shippingCost);
	}

	@GetMapping("/get-shipping-cost-by-pharmacy")
	public ResponseEntity<BigDecimal> getShippingCostByDelivery(@RequestParam Long accountId, @RequestParam int cartType) {
		BigDecimal totalCartCost = cartService.getTotalCostByAccountAndType(accountId, cartType);
		BigDecimal shippingCost;

		if (totalCartCost.compareTo(new BigDecimal(300000)) >= 0) {
			shippingCost = BigDecimal.ZERO;
		} else {
			shippingCost = new BigDecimal(0);
		}
		return ResponseEntity.ok(shippingCost);
	}
	
	
	@PostMapping("/apply-discount")
	public ResponseEntity<DiscountCalculationResultDTO> applyDiscountCodeToCart(
	        @RequestParam Long accountId,
	        @RequestParam int cartType,
	        @RequestParam String discountCode
	) {
	    try {
	        BigDecimal totalCartCost = cartService.getTotalCostByAccountAndTypeWithShipping(accountId, cartType);
	        DiscountCode code = discountCodeService.getDiscountCodeByCode(discountCode);

	        if (code != null && cartService.isDiscountCodeValid(code)) {
	            BigDecimal conditionAmount = BigDecimal.valueOf(code.getCondition());
	            if (totalCartCost.compareTo(conditionAmount) >= 0) {
	                DiscountCalculationResultDTO result = cartService.applyDiscountCodeToCart(totalCartCost, code);
	                return ResponseEntity.ok(result);
	            } else {
	                return ResponseEntity.badRequest().body(null);
	            }
	        } else {
	            return ResponseEntity.badRequest().body(null);
	        }
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(null);
	    }
	}
	
	@PostMapping("/apply-discount-by-pharmacy")
	public ResponseEntity<DiscountCalculationResultDTO> applyDiscountCodeToCartByPharmacy(
	        @RequestParam Long accountId,
	        @RequestParam int cartType,
	        @RequestParam String discountCode
	) {
	    try {
	        BigDecimal totalCartCost = cartService.getTotalCostByAccountAndTypeShippingByPharmacy(accountId, cartType);
	        DiscountCode code = discountCodeService.getDiscountCodeByCode(discountCode);

	        if (code != null && cartService.isDiscountCodeValid(code)) {
	            BigDecimal conditionAmount = BigDecimal.valueOf(code.getCondition());
	            if (totalCartCost.compareTo(conditionAmount) >= 0) {
	                DiscountCalculationResultDTO result = cartService.applyDiscountCodeToCart(totalCartCost, code);
	                return ResponseEntity.ok(result);
	            } else {
	                return ResponseEntity.badRequest().body(null);
	            }
	        } else {
	            return ResponseEntity.badRequest().body(null);
	        }
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(null);
	    }
	}


}
