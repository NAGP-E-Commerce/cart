package com.nagp.cart.service;

import org.springframework.stereotype.Service;

import com.nagp.cart.dto.CartDTO;
import com.nagp.cart.dto.CartEntryDTO;

@Service
public interface CartService {

	CartEntryDTO addToCart(String productCode, Long cartId, Long quantity);

	CartDTO getCartById(Long cartId);

	CartDTO findCartByUser(String userId);

	CartDTO createCart();

	void removeProductFromCart(String productCode, Long cartId, Long quantity);
}
