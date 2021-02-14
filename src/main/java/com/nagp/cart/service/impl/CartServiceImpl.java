package com.nagp.cart.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.nagp.cart.dto.CartDTO;
import com.nagp.cart.dto.CartEntryDTO;
import com.nagp.cart.entity.Cart;
import com.nagp.cart.entity.Entry;
import com.nagp.cart.repo.CartRepository;
import com.nagp.cart.repo.EntryRepository;
import com.nagp.cart.service.CartService;
import com.nagp.product.dto.ProductDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	EntryRepository cartEntryRepository;

	@Autowired
	RestTemplate restTemplate;

	private static final String PRODUCT_SERVICE_URL = "http://localhost:8090/api/ecommerce/";

	@Override
	public CartEntryDTO addToCart(String productCode, Long cartId, Long quantity) {
		Cart cart = null;
		final Optional<Cart> cartOp = cartRepository.findById(cartId);
		if (cartOp.isPresent()) {
			cart = cartOp.get();
		}
		if (cart == null) {
			cart = new Cart();
		}
		ProductDTO product = getProduct(productCode);
		Entry cartEntry = addProductToCart(cart, quantity, product);
		CartEntryDTO cartEntryDTO = new CartEntryDTO();
		populateCartEntry(cartEntry, cartEntryDTO);
		return cartEntryDTO;
	}

	@Override
	public void removeProductFromCart(String productCode, Long cartId, Long quantity) {
		Cart cart = null;
		final Optional<Cart> cartOp = cartRepository.findById(cartId);
		if (cartOp.isPresent()) {
			cart = cartOp.get();
		}
		if (cart != null) {
			ProductDTO product = getProduct(productCode);
			removeProductFromCart(cart, quantity, product);
		}
	}

	@Override
	public CartDTO createCart() {
		Cart cart = new Cart();
		cartRepository.save(cart);
		CartDTO cartDTO = new CartDTO();
		populate(cart, cartDTO);
		return cartDTO;
	}

	@Override
	public CartDTO getCartById(Long cartId) {
		Cart cart = null;
		if (cartId != null) {
			Optional<Cart> cartOp = cartRepository.findById(cartId);
			if (cartOp.isPresent()) {
				cart = cartOp.get();
			} else {
				return null;
			}
		} else {
			cart = new Cart();
			cartRepository.save(cart);
		}
		CartDTO cartDTO = new CartDTO();
		populate(cart, cartDTO);
		return cartDTO;
	}

	private ProductDTO getProduct(String productCode) {
		URI uri = null;
		try {
			uri = new URI(PRODUCT_SERVICE_URL + "product/" + productCode);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		ProductDTO product = restTemplate.getForObject(uri, ProductDTO.class);
		return product;
	}

	private void removeProductFromCart(Cart cart, Long quantity, ProductDTO product) {
		Entry entry = null;
		for (Entry cartEntry : cart.getCartEntries()) {
			if (cartEntry.getProductCode().equalsIgnoreCase(product.getCode())) {
				entry = cartEntry;
			}
		}

		if (Objects.nonNull(entry)) {
			Double entryQuantity = entry.getQuantity();
			if (entryQuantity > quantity) {
				entry.setQuantity(entryQuantity - quantity);
				cartEntryRepository.save(entry);
				cartRepository.save(cart);
			} else {
				cart.setCartEntries(new ArrayList<>());
				cartRepository.save(cart);
			}
		}
	}

	private Entry addProductToCart(Cart cart, Long quantity, ProductDTO product) {
		for (Entry cartEntry : cart.getCartEntries()) {
			if (cartEntry.getProductCode().equalsIgnoreCase(product.getCode())) {
				cartEntry.setQuantity(cartEntry.getQuantity() + quantity);
				cartEntryRepository.save(cartEntry);
				return cartEntry;
			}
		}

		if (CollectionUtils.isEmpty(cart.getCartEntries())) {
			cart.setCartEntries(new ArrayList<>());
		}
		Entry cartEntry = new Entry();
		cartEntry.setProductName(product.getName());
		cartEntry.setProductCode(product.getCode());
		cartEntry.setPrice(product.getPrice());
		cartEntry.setQuantity(product.getUnit());
		cartEntry.setImageUrl(product.getPrimaryImageUrl());
		cartEntryRepository.save(cartEntry);
		cart.getCartEntries().add(cartEntry);
		cartRepository.save(cart);

		return cartEntry;
	}

	public void populateCartEntry(Entry source, CartEntryDTO target) {
		target.setQuantity(source.getQuantity());
		target.setProductCode(source.getProductCode());
		target.setProductName(source.getProductName());
		target.setPrice(source.getPrice());
		target.setTotal(source.getPrice() * source.getQuantity());
		target.setImageUrl(source.getImageUrl());
	}

	private void populate(Cart source, CartDTO target) {
		List<Entry> entries = source.getCartEntries();
		List<CartEntryDTO> cartEntries = new ArrayList<>();
		target.setId(source.getId());
		Double total = Double.valueOf(0);
		Double totalDiscount = Double.valueOf(0);
		Double subTotal = Double.valueOf(0);
		Double tax = Double.valueOf(0);
		if (entries != null && entries.size() > 0) {
			for (Entry entry : entries) {
				CartEntryDTO cartEntry = new CartEntryDTO();
				populateCartEntry(entry, cartEntry);
				cartEntries.add(cartEntry);
				subTotal += cartEntry.getPrice() * cartEntry.getQuantity();
			}
		}

		tax = subTotal / 10;
		total = subTotal + tax;
		target.setGrossTotal(subTotal);
		target.setTax(tax);
		target.setItemsTotal(total);
		target.setTotalDiscount(totalDiscount);
		target.setItems(cartEntries);
//		target.setStatus("Active");
		target.setUserId(source.getUserId());

	}

	@Override
	public CartDTO findCartByUser(String userId) {
		Cart cart = cartRepository.findByUserId(userId);
		CartDTO cartData = new CartDTO();
		if (cart != null) {
			populate(cart, cartData);
		}
		populate(cart, cartData);
		return cartData;
	}

}
