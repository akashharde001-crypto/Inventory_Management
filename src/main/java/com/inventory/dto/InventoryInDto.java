package com.inventory.dto;

import java.util.Map;

public class InventoryInDto {
	
	private String uid;
	private Map<Long, Integer> products;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Map<Long, Integer> getProducts() {
		return products;
	}
	public void setProducts(Map<Long, Integer> products) {
		this.products = products;
	}
	public InventoryInDto(String uid, Map<Long, Integer> products) {
		this.uid = uid;
		this.products = products;
	}
	@Override
	public String toString() {
		return "InventoryInDto [uid=" + uid + ", products=" + products + "]";
	}
	public InventoryInDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
