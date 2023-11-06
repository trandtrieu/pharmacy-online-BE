package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
	
	
	private int cartId;
	private ProductDetailDTO productDetail;
	private int quantity;
	private int cart_type;
	
	
	
	public CartItemDTO(ProductDetailDTO productDetail, int quantity, int cart_type) {
		super();
		this.productDetail = productDetail;
		this.quantity = quantity;
		this.cart_type = cart_type;
	}

}
