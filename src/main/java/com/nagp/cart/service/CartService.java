package com.nagp.cart.service;

import org.springframework.stereotype.Service;

import com.nagp.cart.dto.CartDTO;
import com.nagp.cart.dto.CartEntryDTO;
import com.nagp.cart.dto.PlaceOrderRequestDTO;

@Service
public interface CartService {

	CartEntryDTO addToCart(String productId, Long cartId, Long quantity);

	CartDTO getCartById(Long cartId);

	CartDTO findCartByUser(String userId);

	CartDTO createCart(String userId);

	void removeProductFromCart(String productId, Long cartId, Long quantity);
	
	boolean placeOrder(PlaceOrderRequestDTO cartId);
}
