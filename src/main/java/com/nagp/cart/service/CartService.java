package com.nagp.cart.service;

import org.springframework.stereotype.Service;

import com.nagp.cart.dto.CartEntryDTO;

@Service
public interface CartService {

	CartEntryDTO addToCart(String productCode, Long cartId, Long quantity);
//	
//	CartEntryDTO getCartById(Long cartId);
//	
//	CartEntryDTO findCartByUser(String userId);
}
