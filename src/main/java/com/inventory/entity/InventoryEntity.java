package com.inventory.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "inventory")
public class InventoryEntity {

	@Id
	@Column(name = "product_id")
	private Long productId;

	private int stock;
	@Column(name = "product_name")
	private String productName;

	// Default constructor
	public InventoryEntity() {
	}

	// Parameterized constructor
	public InventoryEntity(Long productId, int stock, String productName) {
		this.productId = productId;
		this.stock = stock;
		this.productName = productName;
	}

	// Getters and Setters
	public Long getProduct_id() {
		return productId;
	}

	public void setProduct_id(Long productId) {
		this.productId = productId;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getProduct_Name() {
		return productName;
	}

	public void setProduct_Name(String productName) {
		this.productName = productName;
	}
}
