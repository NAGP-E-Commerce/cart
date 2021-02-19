package com.nagp.cart.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.nagp.cart.dto.CartDTO;
import com.nagp.cart.dto.CartEntryDTO;
import com.nagp.cart.dto.CartStatus;
import com.nagp.cart.dto.PlaceOrderRequestDTO;
import com.nagp.cart.entity.Cart;
import com.nagp.cart.entity.Entry;
import com.nagp.cart.repo.CartRepository;
import com.nagp.cart.repo.EntryRepository;
import com.nagp.cart.service.CartService;
import com.nagp.product.dto.ProductDTO;
import com.nagp.stock.dto.ProductStockDTO;

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

	private static final String INVENTORY_SERVICE_URL = "http://localhost:8091/im/api/ecommerce/";

	@Override
	public CartEntryDTO addToCart(String productId, Long cartId, Long quantity) {
		Cart cart = null;
		final Optional<Cart> cartOp = cartRepository.findById(cartId);
		if (cartOp.isPresent()) {
			cart = cartOp.get();
		}
		if (cart == null) {
			cart = new Cart();
		}
		ProductDTO product = getProduct(productId);
		if (product.getAvailableQty() > 0) {
			Entry cartEntry = addProductToCart(cart, quantity, product);
			reserveStock(cartEntry.getProductId(), quantity);
			CartEntryDTO cartEntryDTO = new CartEntryDTO();
			populateCartEntry(cartEntry, cartEntryDTO);
			return cartEntryDTO;
		} else {
			return null;
		}
	}

	@Override
	public void removeProductFromCart(String productId, Long cartId, Long quantity) {
		Cart cart = null;
		final Optional<Cart> cartOp = cartRepository.findById(cartId);
		if (cartOp.isPresent()) {
			cart = cartOp.get();
		}
		if (cart != null) {
			ProductDTO product = getProduct(productId);
			removeProductFromCart(cart, quantity, product);
		}
	}

	@Override
	public CartDTO createCart(String userId) {
		
		Cart existingCart = cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE.toString());
		
		if(Objects.nonNull(existingCart)) {
			CartDTO cartDTO = new CartDTO();
			populate(existingCart, cartDTO);
			return cartDTO;
		}
		
		Cart cart = new Cart();
		cart.setStatus(CartStatus.ACTIVE.toString());
		cart.setUserId(userId);
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

	private ProductDTO getProduct(String productId) {
		URI uri = null;
		try {
			uri = new URI(PRODUCT_SERVICE_URL + "product/" + productId);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		ProductDTO product = restTemplate.getForObject(uri, ProductDTO.class);
		return product;
	}

	private void removeProductFromCart(Cart cart, Long quantity, ProductDTO product) {
		Entry entry = null;
		for (Entry cartEntry : cart.getCartEntries()) {
			if (cartEntry.getProductId().equalsIgnoreCase(product.getProductId())) {
				entry = cartEntry;
			}
		}

		if (Objects.nonNull(entry)) {
			Double entryQuantity = entry.getQuantity();
			if (entryQuantity > quantity) {
				entry.setQuantity(entryQuantity - quantity);
				cartEntryRepository.save(entry);
			} else {
				cartEntryRepository.delete(entry);
			}
		}
	}

	private Entry addProductToCart(Cart cart, Long quantity, ProductDTO product) {
		for (Entry cartEntry : cart.getCartEntries()) {
			if (cartEntry.getProductId().equalsIgnoreCase(product.getProductId())) {
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
		cartEntry.setProductId(product.getProductId());
		cartEntry.setPrice(product.getPrice());
		cartEntry.setQuantity(Double.valueOf(quantity.toString()));
		cartEntry.setImageUrl(product.getPrimaryImageUrl());
		cartEntryRepository.save(cartEntry);
		cart.getCartEntries().add(cartEntry);
		cartRepository.save(cart);

		return cartEntry;
	}

	public void populateCartEntry(Entry source, CartEntryDTO target) {
		target.setQuantity(source.getQuantity());
		target.setProductId(source.getProductId());
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
		target.setStatus(source.getStatus());
		target.setUserId(source.getUserId());

	}

	@Override
	public CartDTO findCartByUser(String userId) {
		Cart cart = cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE.toString());
		CartDTO cartData = new CartDTO();
		if (cart != null) {
			populate(cart, cartData);
		} else {
			return createCart(userId);
		}
		return cartData;
	}

	@Override
	public boolean placeOrder(PlaceOrderRequestDTO placeOrderRequestDTO) {
		Cart cart = null;
		Optional<Cart> cartOp = cartRepository.findById(placeOrderRequestDTO.getCartId());
		if (cartOp.isPresent()) {
			cart = cartOp.get();
		}
		cart.setStatus(CartStatus.ORDERED.toString());
		cartRepository.save(cart);
		commitStock(cart.getCartEntries());
		CartDTO cartData = new CartDTO();
		populate(cart, cartData);
		return true;

	}

	private void reserveStock(String productId, Long quantity) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		ProductStockDTO productStockDTO = new ProductStockDTO();
		productStockDTO.setProductId(productId);
		productStockDTO.setQuantity(quantity.intValue());
		HttpEntity<ProductStockDTO> httpEntity = new HttpEntity<>(productStockDTO, headers);

		URI uri = null;
		try {
			uri = new URI(INVENTORY_SERVICE_URL + "inventory/reserve");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		restTemplate.postForObject(uri, httpEntity, Boolean.class);
	}

	private void commitStock(List<Entry> cartEntries) {
		for (Entry entry : cartEntries) {
			commitStock(entry.getProductId(), entry.getQuantity());
		}
	}

	private void commitStock(String productId, Double quantity) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		ProductStockDTO productStockDTO = new ProductStockDTO();
		productStockDTO.setProductId(productId);
		productStockDTO.setQuantity(quantity.intValue());
		HttpEntity<ProductStockDTO> httpEntity = new HttpEntity<>(productStockDTO, headers);

		URI uri = null;
		try {
			uri = new URI(INVENTORY_SERVICE_URL + "inventory/commit");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		restTemplate.postForObject(uri, httpEntity, Boolean.class);
	}
}
