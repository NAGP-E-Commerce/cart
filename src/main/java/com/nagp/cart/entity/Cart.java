package com.nagp.cart.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

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
}
