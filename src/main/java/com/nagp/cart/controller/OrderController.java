package com.nagp.cart.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nagp.cart.dto.CartDTO;
import com.nagp.cart.dto.PlaceOrderRequestDTO;
import com.nagp.cart.service.CartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "OrderControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/order")
@Validated
public class OrderController {

	@Autowired
	private CartService cartService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/create/{cartId}", method = RequestMethod.POST)
	@ApiOperation("Place order")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = PlaceOrderRequestDTO.class) })
	public boolean placeOrder(@PathVariable("cartId") @NotBlank(message = "id must not be empty") String cartId) {
		return cartService.placeOrder(cartId);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@ApiOperation("Get Order by userId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = CartDTO.class) })
	public List<CartDTO> getOrdersByUserId(
			@PathVariable("userId") @NotBlank(message = "id must not be empty") String userId) {
		return cartService.findOrdersByUserId(userId);
	}

}
