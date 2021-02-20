package com.nagp.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nagp.cart.dto.CartDTO;
import com.nagp.cart.dto.CartEntryDTO;

@Service
public interface CartService {

	CartEntryDTO addToCart(String productId, Long cartId, Long quantity);

	CartDTO getCartById(Long cartId);

	CartDTO findCartByUser(String userId);

	CartDTO createCart(String userId);

	void removeProductFromCart(String productId, Long cartId, Long quantity);

	boolean placeOrder(String cartId);

	List<CartDTO> findOrdersByUserId(String userId);
}
