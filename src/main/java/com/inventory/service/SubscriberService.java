package com.inventory.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.inventory.dto.InventoryInDto;
import com.inventory.dto.ResponseOutDto;
import com.inventory.entity.InventoryEntity;
import com.inventory.repository.InventoryRepostory;

@Service
public class SubscriberService {

	@Autowired
	InventoryRepostory inventoryRepostory;
	
    @ServiceActivator(inputChannel = "myInputChannel")
    public void messageReceiver(String payload, 
                                @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) throws JsonMappingException, JsonProcessingException {
    	
//    	JsonObject json = JsonParser.parseString(payload).getAsJsonObject();
//    	JsonElement jsonElement = json.get("products");
    	
//    	System.out.println("products: ");
    	 message.ack();
    	ObjectMapper mapper = new ObjectMapper();
        InventoryInDto order = mapper.readValue(payload, InventoryInDto.class);
            System.out.println("Message received from Google Cloud Pub/Sub: " + order.toString());
            Map<Long, Integer> products = order.getProducts();
            for(long product : order.getProducts().keySet()) {
    			Optional<InventoryEntity> checkProduct = inventoryRepostory.findById(product);
    			if(checkProduct.isEmpty())
    			{
    				System.out.println("Product not present!!");
    			}
    			InventoryEntity inventoryEntity = checkProduct.get();
    			int stock = inventoryEntity.getStock();
    			Integer requestedQuantity = products.get(product);
    			   
    			
    			if(requestedQuantity>stock)
    			{
    				System.out.println("Stock is not sufficient for productId "+ product + "!!");
    			}
    			else {
    				System.out.println("All products are available, Order placed." );
    			}
    			
            }
    
			// Acknowledge the message upon successful processing
//            System.out.println("All products are available, Order placed." );
  }
}
