package com.inventory.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.dto.InventoryInDto;
import com.inventory.dto.ResponseOutDto;
import com.inventory.service.InventoryService;


@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	InventoryService inventoryService;
	
	@GetMapping("/stocks")
	public ResponseOutDto validateInventory(@RequestBody InventoryInDto inventoryInDto) {
		return inventoryService.validateInvantory(inventoryInDto);
	}
}
