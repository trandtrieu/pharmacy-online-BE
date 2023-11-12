package com.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.CartItemDTO;
import com.dto.DiscountCalculationResultDTO;
import com.dto.ProductDetailDTO;
import com.model.Account;
import com.model.Cart;
import com.model.CartItem;
import com.model.DiscountCode;
import com.model.Product;
import com.model.Product_image;
import com.repository.AccountRepository;
import com.repository.CartItemRepository;
import com.repository.CartRepository;
import com.repository.ProductRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

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
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Autowired
	private DiscountCodeService discountCodeService;

	public void addToCart(Long accountId, int productId, int quantity, int cart_type) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

		Cart cart = account.getCart();
		if (cart == null) {
			cart = new Cart();
			cart.setAccount(account);
		}

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

		// Check if there's an existing cart item with the same cart_type and product
		CartItem existingCartItem = cart.getCartItems().stream()
				.filter(item -> item.getCart_type() == cart_type && item.getProduct().equals(product)).findFirst()
				.orElse(null);

		if (existingCartItem != null) {
			existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
		} else {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setCart_type(cart_type);
			cartItem.setCart(cart);
			cart.getCartItems().add(cartItem);
		}

		cartRepository.save(cart);
	}

	public List<CartItemDTO> getCartItems(Long accountId, int cart_type) {
		Account account = accountRepository.findById(accountId).orElse(null);

		if (account != null && account.getCart() != null) {
			Cart cart = account.getCart();
			List<CartItem> cartItems = cart.getCartItems();

			List<CartItemDTO> productDTOs = new ArrayList<>();
			for (CartItem cartItem : cartItems) {
				if (cartItem.getCart_type() == cart_type) {
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
							cartItem.getQuantity(), cartItem.getCart_type());
					productDTOs.add(productWithQuantity);
				}
			}

			return productDTOs;
		} else {
			return Collections.emptyList();
		}
	}

	public void removeFromCart(Integer cartId) {
		CartItem cartItem = cartItemRepository.findById(cartId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));

		cartItemRepository.delete(cartItem);
	}

	@Transactional
	public void removeAllCartItems(List<CartItemDTO> cartItems) {
		try {
			for (CartItemDTO cartItemDTO : cartItems) {
				int cartItemId = cartItemDTO.getCartId();
				Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

				if (cartItemOptional.isPresent()) {
					CartItem cartItem = cartItemOptional.get();
					cartItemRepository.delete(cartItem);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error removing cart items: " + e.getMessage(), e);
		}
	}

//	public void clearCartByAccountAndType(Long accountId, int cartType) {
//	    Account account = accountRepository.findById(accountId).orElse(null);
//
//	    if (account != null && account.getCart() != null) {
//	        Cart cart = account.getCart();
//	        List<CartItem> itemsToRemove = cart.getCartItems()
//	            .stream()
//	            .filter(item -> item.getCart_type() == cartType)
//	            .collect(Collectors.toList());
//	        cart.getCartItems().removeAll(itemsToRemove);
//	        cartRepository.save(cart);
//	    }
//	}
	@Transactional
	public void clearCartByAccountAndType(Long accountId, int cartType) {
		String sql = "DELETE FROM cart_item WHERE cart_id IN (SELECT id FROM cart WHERE account_id = :accountId) AND cart_type = :cartType";

		int deletedCount = entityManager.createNativeQuery(sql).setParameter("accountId", accountId)
				.setParameter("cartType", cartType).executeUpdate();

		System.out.println("Deleted " + deletedCount + " cart items.");
	}

	public void updateCartItems(List<CartItemDTO> updatedCartItems) {
		for (CartItemDTO updatedCartItem : updatedCartItems) {
			int cartItemId = updatedCartItem.getCartId();
			int newQuantity = updatedCartItem.getQuantity();

			Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

			if (cartItemOptional.isPresent()) {
				CartItem cartItem = cartItemOptional.get();
				cartItem.setQuantity(newQuantity);
				cartItemRepository.save(cartItem);
			} else {
				throw new RuntimeException("Cart item with ID " + cartItemId + " not found.");
			}
		}
	}

	public int getTotalQuantityInCart(Long accountId, int cart_type) {
		Account account = accountRepository.findById(accountId).orElse(null);

		if (account != null && account.getCart() != null) {
			Cart cart = account.getCart();
			List<CartItem> cartItems = cart.getCartItems();

			int totalQuantity = 0;
			for (CartItem cartItem : cartItems) {
				if (cartItem.getCart_type() == cart_type) {
					totalQuantity += cartItem.getQuantity();
				}
			}
			return totalQuantity;
		} else {
			return 0;
		}
	}

	public int countProductsInCart(Long accountId, int cart_type) {
		Account account = accountRepository.findById(accountId).orElse(null);

		if (account != null && account.getCart() != null) {
			Cart cart = account.getCart();
			List<CartItem> cartItems = cart.getCartItems();

			int uniqueProductCount = 0;
			for (CartItem cartItem : cartItems) {
				if (cartItem.getCart_type() == cart_type) { // Check if cart_type matches
					uniqueProductCount++;
				}
			}

			return uniqueProductCount;
		} else {
			return 0;
		}
	}

	public BigDecimal getTotalCostByAccountAndType(Long accountId, int cartType) {
		Account account = accountRepository.findById(accountId).orElse(null);
		BigDecimal totalCost = BigDecimal.ZERO;

		if (account != null && account.getCart() != null) {
			Cart cart = account.getCart();
			List<CartItem> cartItems = cart.getCartItems();

			for (CartItem cartItem : cartItems) {
				if (cartItem.getCart_type() == cartType) {
					BigDecimal itemCost = BigDecimal.valueOf(cartItem.getQuantity())
							.multiply(cartItem.getProduct().getP_price());
					totalCost = totalCost.add(itemCost);
				}
			}
		}
		return totalCost;
	}

	public BigDecimal getTotalCostByAccountAndTypeWithShipping(Long accountId, int cartType) {
		BigDecimal totalCost = getTotalCostByAccountAndType(accountId, cartType);

		BigDecimal shippingCost = new BigDecimal("0");
		BigDecimal freeShippingThreshold = new BigDecimal("100");
		BigDecimal shippingRate = new BigDecimal("10");

		if (totalCost.compareTo(freeShippingThreshold) < 0) {
			shippingCost = shippingRate;
		}

		return totalCost.add(shippingCost);
	}

	public BigDecimal applyDiscountCode(BigDecimal totalCost, DiscountCode discountCode) {
		if (discountCode != null && discountCode.getExpiryDate().isAfter(LocalDateTime.now())
				&& discountCode.getTimesUsable() > 0) {
			BigDecimal discountAmount = totalCost
					.multiply(BigDecimal.valueOf(discountCode.getDiscountPercentage() / 100));
			totalCost = totalCost.subtract(discountAmount);
			discountCode.setTimesUsable(discountCode.getTimesUsable() - 1);
		}
		return totalCost;
	}
	
	
	public DiscountCalculationResultDTO applyDiscountCodeToCart(BigDecimal totalCartCost, DiscountCode discountCode) {
		DiscountCalculationResultDTO result = new DiscountCalculationResultDTO();
	    if (isDiscountCodeValid(discountCode)) {
	        result.setDiscountAmount(totalCartCost.multiply(BigDecimal.valueOf(discountCode.getDiscountPercentage() / 100)));
	        result.setTotalCostAfterDiscount(totalCartCost.subtract(result.getDiscountAmount()));
	        discountCode.setTimesUsable(discountCode.getTimesUsable() - 1);
	        discountCodeService.updateDiscountCode(discountCode); // Update discount code info
	    }
	    return result;
	}

	
//	public BigDecimal applyDiscountCodeToCart(BigDecimal totalCartCost, DiscountCode discountCode) {
//	    if (isDiscountCodeValid(discountCode)) {
//	        totalCartCost = applyDiscount(totalCartCost, discountCode);
//	    }
//	    return totalCartCost;
//	}

//	private BigDecimal applyDiscount(BigDecimal totalCartCost, DiscountCode discountCode) {
//	    BigDecimal discountAmount = totalCartCost.multiply(BigDecimal.valueOf(discountCode.getDiscountPercentage() / 100));
//	    totalCartCost = totalCartCost.subtract(discountAmount);
//	    discountCode.setTimesUsable(discountCode.getTimesUsable() - 1);
//	    discountCodeService.updateDiscountCode(discountCode); // Update discount code info
//	    return totalCartCost;
//	}


	public boolean isDiscountCodeValid(DiscountCode discountCode) {
	    return discountCode.getExpiryDate().isAfter(LocalDateTime.now()) &&
	           discountCode.getTimesUsable() > 0 &&
	           discountCode.getStatus() == 1; // Assuming status 1 means the code is active
	}




}
