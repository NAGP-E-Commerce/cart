package com.nagp.product.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ProductCategoryDTO implements Serializable {

	private static final long serialVersionUID = -1858532907884746949L;

	private String id;

	private String name;

	private String superCategory;

	private String similarCategory;

	private List<ProductDTO> product;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuperCategory() {
		return superCategory;
	}

	public void setSuperCategory(String superCategory) {
		this.superCategory = superCategory;
	}

	public String getSimilarCategory() {
		return similarCategory;
	}

	public void setSimilarCategory(String similarCategory) {
		this.similarCategory = similarCategory;
	}

	public List<ProductDTO> getProduct() {
		return product;
	}

	public void setProduct(List<ProductDTO> product) {
		this.product = product;
	}

}
