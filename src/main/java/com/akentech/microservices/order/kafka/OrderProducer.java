package com.akentech.microservices.order.kafka;

import com.akentech.microservices.order.dto.OrderResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, OrderResponse> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderResponse orderResponse) {
        kafkaTemplate.send("order-created", orderResponse);
    }
}