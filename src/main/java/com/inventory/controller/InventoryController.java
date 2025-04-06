package com.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventory.dto.InventoryInDto;
import com.inventory.dto.ResponseOutDto;
import com.inventory.dto.RestockRequestDto;
import com.inventory.entity.InventoryEntity;
import com.inventory.repository.InventoryRepostory;
import com.inventory.service.InventoryService;
//import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    InventoryRepostory inventoryRepository;

    @GetMapping("/stocks")
    public ResponseOutDto validateInventory(@RequestBody InventoryInDto inventoryInDto) {
        return inventoryService.validateInvantory(inventoryInDto);
    }

    // ✅ New Restock API
//    @PostMapping("/restock")
////    public ResponseEntity<String> restockInventory(@RequestBody RestockRequestDto request)
//    public ResponseEntity<String> restockInventory(@RequestBody RestockRequestDto request){
//        Optional<InventoryEntity> optionalProduct = inventoryRepository.findById(request.getProductId());
//
//        if (optionalProduct.isEmpty()) {
//            return ResponseEntity.badRequest().body("Product not found with ID: " + request.getProductId());
//        }
//
//        InventoryEntity product = optionalProduct.get();
//        product.setStock(product.getStock() + request.getQuantity());
//        inventoryRepository.save(product);
//
//        return ResponseEntity.ok("Stock updated for product ID " + request.getProductId() +
//                ". New stock: " + product.getStock());
//    }
    @PostMapping("/restock")
    public ResponseEntity<String> restockInventory(@RequestBody RestockRequestDto request) {
        StringBuilder responseMessage = new StringBuilder();

        for (RestockRequestDto.ProductQuantityDto item : request.getRestockList()) {
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

}
