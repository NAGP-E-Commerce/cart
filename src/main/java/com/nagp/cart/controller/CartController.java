package com.nagp.cart.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nagp.cart.dto.AddProductToCartRequestDTO;
import com.nagp.cart.dto.CartDTO;
import com.nagp.cart.dto.CartEntryDTO;
import com.nagp.cart.dto.PlaceOrderRequestDTO;
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

    @CrossOrigin(origins = "*")
	@RequestMapping(value = "/entry", method = RequestMethod.POST)
	@ApiOperation("Add product to cart")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = CartEntryDTO.class) })
	public CartEntryDTO addProductToCart(@RequestBody AddProductToCartRequestDTO addToCartRequest) {
		return cartService.addToCart(addToCartRequest.getProductId(), addToCartRequest.getCartId(),
				addToCartRequest.getQuantity());
	}

    @CrossOrigin(origins = "*")
	@RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
	@ApiOperation("Get cart by cartId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = CartDTO.class) })
	public CartDTO getCartById(@PathVariable("cartId") @NotBlank(message = "id must not be empty") String cartId) {
		return cartService.getCartById(Long.parseLong(cartId));
	}

    @CrossOrigin(origins = "*")
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	@ApiOperation("Get cart by userId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = CartDTO.class) })
	public CartDTO getCartByUserId(@PathVariable("userId") @NotBlank(message = "id must not be empty") String userId) {
		return cartService.findCartByUser(userId);
	}
	

    @CrossOrigin(origins = "*")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation("Create cart")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = CartDTO.class) })
	public CartDTO createCart() {
		return cartService.createCart();
	}
	
    @CrossOrigin(origins = "*")
	@RequestMapping(value = "/entry", method = RequestMethod.PUT)
	@ApiOperation("Add product to cart")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = CartEntryDTO.class) })
	public void removeProductFromCart(@RequestBody AddProductToCartRequestDTO addToCartRequest) {
		cartService.removeProductFromCart(addToCartRequest.getProductId(), addToCartRequest.getCartId(),
				addToCartRequest.getQuantity());
	}
    
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/place/order", method = RequestMethod.PUT)
    @ApiOperation("Place order")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = PlaceOrderRequestDTO.class) })
	public boolean placeOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequestDTO)
	{
		return cartService.placeOrder(placeOrderRequestDTO);
	}
}
