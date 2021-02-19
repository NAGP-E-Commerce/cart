package com.nagp.cart.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagp.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUserIdAndStatus(String userId, String status);
}
