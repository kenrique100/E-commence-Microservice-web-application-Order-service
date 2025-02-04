package com.akentech.microservices.order.service;

import com.akentech.microservices.order.dto.OrderRequest;
import com.akentech.microservices.order.dto.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
    List<OrderResponse> getAllOrders();
    Optional<OrderResponse> getOrderById(Long id);
    void updateOrder(Long id, OrderRequest orderRequest);
    void deleteOrder(Long id);
}