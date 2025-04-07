package com.inventory.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.dto.InventoryInDto;
import com.inventory.dto.ResponseOutDto;
import com.inventory.entity.InventoryEntity;
import com.inventory.repository.InventoryRepostory;

import jakarta.transaction.Transactional;

@Service
public class InventoryService {
	
	@Autowired
	InventoryRepostory inventoryRepostory;
	
	@Transactional
	public ResponseOutDto validateInvantory(InventoryInDto inventoryInDto)
	{
		Map<Long, Integer> products = inventoryInDto.getProducts();
		for(long product : products.keySet()) {
			Optional<InventoryEntity> checkProduct = inventoryRepostory.findById(product);
			if(checkProduct.isEmpty())
			{
				return new ResponseOutDto("Product not present!!",-1);
			}
			InventoryEntity inventoryEntity = checkProduct.get();
			int stock = inventoryEntity.getStock();
			Integer requestedQuantity = products.get(product);
			
			if(requestedQuantity>stock)
			{
				return new ResponseOutDto("Stock is not sufficient for productId "+ product + "!!",-2);
			}
			inventoryEntity.setStock(stock-requestedQuantity);
			inventoryRepostory.save(inventoryEntity);
		}
		
		return new ResponseOutDto("All products are available",1);
	}

	public int getStock(long productId) throws Exception {
		Optional<InventoryEntity> checkProduct = inventoryRepostory.findById(productId);
		
		if(checkProduct.isEmpty())
		{
			throw new Exception("product not found");
		}
		
		InventoryEntity product = checkProduct.get();
		return product.getStock();
		
	}

}
