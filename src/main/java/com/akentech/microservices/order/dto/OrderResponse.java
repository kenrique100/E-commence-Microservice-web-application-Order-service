package com.akentech.microservices.order.dto;

import java.math.BigDecimal;

public record OrderResponse(Long id, String orderNumber, String skuCode, BigDecimal price, Integer quantity, String productId, String inventoryId) {
}