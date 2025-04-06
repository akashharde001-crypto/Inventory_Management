package com.inventory.dto;

public class ProductQuantityDto {

	 private Long productId;
     private int quantity;

     public Long getProductId() {
         return productId;
     }

     public void setProductId(Long productId) {
         this.productId = productId;
     }

     public int getQuantity() {
         return quantity;
     }

     public void setQuantity(int quantity) {
         this.quantity = quantity;
     }

	public ProductQuantityDto(Long productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public ProductQuantityDto() {
		super();
		// TODO Auto-generated constructor stub
	}
     
     
}
