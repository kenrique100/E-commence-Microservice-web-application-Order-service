package com.akentech.microservices.product.kafka;

import com.akentech.microservices.product.dto.ProductResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {

    @KafkaListener(topics = "order-created", groupId = "product-service-group")
    public void consumeOrderCreatedEvent(ProductResponse productResponse) {
        // Handle the order created event
        System.out.println("Received order created event: " + productResponse);
    }
}