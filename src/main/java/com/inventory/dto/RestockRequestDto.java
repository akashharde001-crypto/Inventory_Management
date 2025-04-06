//package com.inventory.dto;
//
//public class RestockRequestDto {
//    private Long productId;
//    private int quantity;
//
//    public Long getProductId() {
//        return productId;
//    }
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
//    public int getQuantity() {
//        return quantity;
//    }
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//}
package com.inventory.dto;

import java.util.List;

public class RestockRequestDto {

    private List<ProductQuantityDto> restockList;

    public List<ProductQuantityDto> getRestockList() {
        return restockList;
    }

    public void setRestockList(List<ProductQuantityDto> restockList) {
        this.restockList = restockList;
    }

}
