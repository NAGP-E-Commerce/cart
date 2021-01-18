package com.nagp.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagp.cart.dto.AddProductToCartRequestDTO;
import com.nagp.cart.dto.CartEntryDTO;
import com.nagp.cart.service.CartService;
import com.nagp.product.dto.ProductDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@Api(value = "CartControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/carts")
@Validated
public class CartController {

	@Autowired
	private CartService cartService;
	
	@PostMapping(value = "/add/entry", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Add product to cart")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ProductDTO.class)})
	public CartEntryDTO addProductToCart(@RequestBody AddProductToCartRequestDTO addToCartRequest)
	{
		return cartService.addToCart(addToCartRequest.getProductCode(), addToCartRequest.getCartId(), addToCartRequest.getQuantity());
	}
}
