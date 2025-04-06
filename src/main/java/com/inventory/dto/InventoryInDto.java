package com.inventory.dto;

import java.util.Map;

public class InventoryInDto {
	
	private Map<Long, Integer> products;

	public Map<Long, Integer> getProducts() {
		return products;
	}

	public void setProducts(Map<Long, Integer> products) {
		this.products = products;
	}

	public InventoryInDto(Map<Long, Integer> products) {
		super();
		this.products = products;
	}

	public InventoryInDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
