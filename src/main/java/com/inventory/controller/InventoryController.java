package com.inventory.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.dto.InventoryInDto;
import com.inventory.dto.ProductQuantityDto;
import com.inventory.dto.ResponseOutDto;
import com.inventory.dto.RestockRequestDto;
import com.inventory.entity.InventoryEntity;
import com.inventory.repository.InventoryRepostory;
import com.inventory.service.InventoryService;
//import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    InventoryRepostory inventoryRepository;

    @PostMapping("/stocks/validate")
    public ResponseOutDto validateInventory(@RequestBody InventoryInDto inventoryInDto) {
        return inventoryService.validateInvantory(inventoryInDto);
    }

    @PostMapping("/restock")
    public ResponseEntity<String> restockInventory(@RequestBody RestockRequestDto request) {
        StringBuilder responseMessage = new StringBuilder();

        for (ProductQuantityDto item : request.getRestockList()) {
            Optional<InventoryEntity> optionalProduct = inventoryRepository.findById(item.getProductId());

            if (optionalProduct.isEmpty()) {
                responseMessage.append("Product not found with ID: ")
                               .append(item.getProductId()).append("\n");
                continue;
            }

            InventoryEntity product = optionalProduct.get();
            product.setStock(product.getStock() + item.getQuantity());
            inventoryRepository.save(product);

            responseMessage.append("Stock Updated for product ID ")
                           .append(item.getProductId()).append(". New stock: ")
                           .append(product.getStock()).append("\n");
        }

        return ResponseEntity.ok(responseMessage.toString());
    }
    
    @GetMapping("/stock/{productId}")
    public int getStock(@PathVariable("productId") int productId) throws Exception
    {
    	return inventoryService.getStock(productId);
    }

}
