package com.nagp.cart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


@Data
@Entity(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	private String userId;
	
	private String productName;
	
	private String productCode;
	
	private Double price;
	
	private Long quantity;
	
	private String imageUrl;
}
