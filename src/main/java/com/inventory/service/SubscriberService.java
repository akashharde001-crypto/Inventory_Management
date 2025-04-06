package com.inventory.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.inventory.dto.InventoryInDto;
import com.inventory.entity.InventoryEntity;
import com.inventory.repository.InventoryRepostory;

@Service
public class SubscriberService {

    @Autowired
    private InventoryRepostory inventoryRepostory;

    @Autowired
    private PubSubTemplate pubSubTemplate;

    @ServiceActivator(inputChannel = "myInputChannel")
    public void messageReceiver(String payload,
                                 @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message)
            throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        InventoryInDto order = mapper.readValue(payload, InventoryInDto.class);
        System.out.println("📥 Message received from Google Cloud Pub/Sub: " + order.toString());

        Map<Long, Integer> products = order.getProducts();
        Map<Long, InventoryEntity> productMap = new HashMap<>();
        boolean allAvailable = true;

        // DEBUG: List all products in DB
//        System.out.println("🔍 Current inventory in DB:");
        inventoryRepostory.findAll().forEach(System.out::println);

        // Step 1: Validate stock
        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
            Long productId = entry.getKey();
            Integer requestedQty = entry.getValue();

            Optional<InventoryEntity> productOpt = inventoryRepostory.findById(productId);
            if (productOpt.isEmpty()) {
                System.out.println("❌ Product ID " + productId + " not found in DB.");
                allAvailable = false;
                break;
            }

            InventoryEntity product = productOpt.get();
            System.out.println("✅ Checking Product: " + product);
            System.out.println("🔢 Requested: " + requestedQty + ", In Stock: " + product.getStock());

            if (requestedQty > product.getStock()) {
                System.out.println("❌ Insufficient stock for Product ID " + productId);
                allAvailable = false;
                break;
            }

            productMap.put(productId, product); // Cache for update
        }

        // Step 2: Deduct and save
        if (allAvailable) {
        	for (Map.Entry<Long, Integer> entry : products.entrySet()) {
        	    Long productId = entry.getKey();
        	    Integer requestedQty = entry.getValue();

        	    InventoryEntity product = productMap.get(productId);
        	    product.setStock(product.getStock() - requestedQty);
        	    System.out.println("✅ Order processed successfully for Product ID "+ productId );
              	}

        	// ✅ Save all updated entities in one go
        	inventoryRepostory.saveAll(productMap.values());

//            System.out.println("✅ Order processed successfully for Product ID !!");

         
        } else {
            System.out.println("❌ Order rejected due to stock issues.");
        }

        // Step 4: Acknowledge the message
        message.ack();
    }
}
