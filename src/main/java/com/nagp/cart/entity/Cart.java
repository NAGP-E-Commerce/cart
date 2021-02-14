package com.nagp.cart.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import lombok.Data;


@Data
@Entity(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	@OneToMany
	@JoinTable(name = "cart_entry",
    joinColumns = @JoinColumn(name = "entry_id", referencedColumnName = "id"))
	private List<Entry> cartEntries = new ArrayList<Entry>();
	
	private String userId;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Entry> getCartEntries() {
		return cartEntries;
	}

	public void setCartEntries(List<Entry> cartEntries) {
		this.cartEntries = cartEntries;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
