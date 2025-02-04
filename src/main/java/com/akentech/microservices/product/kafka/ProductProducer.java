package com.akentech.microservices.product.kafka;

import com.akentech.microservices.product.dto.ProductResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductProducer {

    private final KafkaTemplate<String, ProductResponse> kafkaTemplate;

    public ProductProducer(KafkaTemplate<String, ProductResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductCreatedEvent(ProductResponse productResponse) {
        kafkaTemplate.send("product-created", productResponse);
    }
}