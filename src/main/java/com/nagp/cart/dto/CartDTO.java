package com.nagp.cart.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CartDTO implements Serializable {

	private static final long serialVersionUID = -4995045878952310183L;

	private List<CartEntryDTO> items;

	private Double grossTotal;

	private Double itemsTotal;

	private Double totalDiscount;

	private Double tax;

	private String userId;

	private long id;
	
	private String status;

	public List<CartEntryDTO> getItems() {
		return items;
	}

	public void setItems(List<CartEntryDTO> items) {
		this.items = items;
	}

	public Double getGrossTotal() {
		return grossTotal;
	}

	public void setGrossTotal(Double grossTotal) {
		this.grossTotal = grossTotal;
	}

	public Double getItemsTotal() {
		return itemsTotal;
	}

	public void setItemsTotal(Double itemsTotal) {
		this.itemsTotal = itemsTotal;
	}

	public Double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
