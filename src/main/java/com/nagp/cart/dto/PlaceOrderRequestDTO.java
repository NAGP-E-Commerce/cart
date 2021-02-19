package com.nagp.cart.dto;

import java.io.Serializable;

public class PlaceOrderRequestDTO implements Serializable{
	
	private static final long serialVersionUID = 954851317309994947L;
	
	private long cartId;

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

}
