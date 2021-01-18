package com.nagp.cart.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagp.cart.dto.CartEntryDTO;
import com.nagp.cart.entity.Cart;
import com.nagp.cart.repo.CartRepository;
import com.nagp.cart.service.CartService;
import com.nagp.product.dto.ProductDTO;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	CartRepository cartRepository;
	
	public CartEntryDTO addToCart(String productCode, Long cartId, Long quantity) {
		Cart cart = cartRepository.getOne(cartId);
		
		ProductDTO product = getProduct(productCode);
		System.out.println("hello");
		return null;
	}
	
	private ProductDTO getProduct(String productCode) 
	{
		return null;
	}
}
