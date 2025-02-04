package com.akentech.microservices.order.kafka;

import com.akentech.microservices.order.dto.OrderResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(topics = "product-created", groupId = "order-service-group")
    public void consumeProductCreatedEvent(OrderResponse orderResponse) {
        // Handle the product created event
        System.out.println("Received product created event: " + orderResponse);
    }
}