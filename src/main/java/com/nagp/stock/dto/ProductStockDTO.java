package com.nagp.stock.dto;

import lombok.Data;

@Data
public class ProductStockDTO {

	private String productId;
	private int quantity;
	private int reserve;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getReserve() {
		return reserve;
	}

	public void setReserve(int reserve) {
		this.reserve = reserve;
	}

}
