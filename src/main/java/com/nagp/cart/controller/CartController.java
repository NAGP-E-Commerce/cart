package com.nagp.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nagp.cart.dto.AddProductToCartRequestDTO;
import com.nagp.cart.dto.CartEntryDTO;
import com.nagp.cart.service.CartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "CartControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/cart")
@Validated
public class CartController {

	@Autowired
	private CartService cartService;

	@RequestMapping(value = "entry", method = RequestMethod.POST)
	@ApiOperation("Add product to cart")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = CartEntryDTO.class)})
	public CartEntryDTO addProductToCart(@RequestBody AddProductToCartRequestDTO addToCartRequest)
	{
		return cartService.addToCart(addToCartRequest.getProductCode(), addToCartRequest.getCartId(), addToCartRequest.getQuantity());
	}
}
