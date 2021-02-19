package com.nagp.product.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 5867934801407522439L;

	private String id;

	private String productId;
	private String name;
	private String code;
	private String title;
	private String description;
	private String brand;
	private Double price;
	private String primaryImageUrl;
	private String secondaryImageUrl;
	private String categoryCode;
	private Integer availableQty;
	private String seller;
	private Boolean active;
	private Boolean isNew;
	private Boolean isFeatured;
	private Boolean isSpecial;
	private Boolean isBestSeller;
	private ProductStatus status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPrimaryImageUrl() {
		return primaryImageUrl;
	}

	public void setPrimaryImageUrl(String primaryImageUrl) {
		this.primaryImageUrl = primaryImageUrl;
	}

	public String getSecondaryImageUrl() {
		return secondaryImageUrl;
	}

	public void setSecondaryImageUrl(String secondaryImageUrl) {
		this.secondaryImageUrl = secondaryImageUrl;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public Integer getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public Boolean getIsFeatured() {
		return isFeatured;
	}

	public void setIsFeatured(Boolean isFeatured) {
		this.isFeatured = isFeatured;
	}

	public Boolean getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Boolean getIsBestSeller() {
		return isBestSeller;
	}

	public void setIsBestSeller(Boolean isBestSeller) {
		this.isBestSeller = isBestSeller;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

}
