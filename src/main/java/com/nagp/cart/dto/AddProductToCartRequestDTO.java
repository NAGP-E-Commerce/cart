package com.nagp.cart.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AddProductToCartRequestDTO implements Serializable {

	private static final long serialVersionUID = -8072999709411264085L;

	private Long cartId;
	private String productId;
	private Long quantity;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}
