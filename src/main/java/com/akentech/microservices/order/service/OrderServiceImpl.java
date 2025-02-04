package com.akentech.microservices.order.service;

import com.akentech.microservices.order.dto.OrderRequest;
import com.akentech.microservices.order.dto.OrderResponse;
import com.akentech.microservices.order.exception.OrderNotFoundException;
import com.akentech.microservices.order.kafka.OrderProducer;
import com.akentech.microservices.order.model.Order;
import com.akentech.microservices.order.repository.OrderRepository;
import com.akentech.microservices.order.util.OrderServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    @Override // Add @Override annotation
    public void placeOrder(OrderRequest orderRequest) {
        log.info("Placing order with SKU Code: {}", orderRequest.skuCode());

        Order order = OrderServiceUtil.mapToOrder(orderRequest);
        orderRepository.save(order);

        log.info("Order placed successfully with ID: {}", order.getId());

        // Send order created event
        orderProducer.sendOrderCreatedEvent(new OrderResponse(order.getId(), order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity(), order.getProductId(), order.getInventoryId()));
    }

    @Override // Add @Override annotation
    public List<OrderResponse> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponse(order.getId(), order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity(), order.getProductId(), order.getInventoryId()))
                .collect(Collectors.toList());
    }

    @Override // Add @Override annotation
    public Optional<OrderResponse> getOrderById(Long id) {
        log.info("Fetching order by ID: {}", id);
        return orderRepository.findById(id)
                .map(order -> new OrderResponse(order.getId(), order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity(), order.getProductId(), order.getInventoryId()));
    }

    @Override // Add @Override annotation
    public void updateOrder(Long id, OrderRequest orderRequest) {
        log.info("Updating order with ID: {}", id);

        Order existingOrder = OrderServiceUtil.findOrderByIdOrThrow(orderRepository, id);
        existingOrder.setPrice(orderRequest.price());
        existingOrder.setSkuCode(orderRequest.skuCode());
        existingOrder.setQuantity(orderRequest.quantity());
        existingOrder.setProductId(orderRequest.productId());
        existingOrder.setInventoryId(orderRequest.inventoryId());

        orderRepository.save(existingOrder);
        log.info("Order updated successfully with ID: {}", id);
    }

    @Override // Add @Override annotation
    public void deleteOrder(Long id) {
        log.info("Deleting order with ID: {}", id);

        if (!orderRepository.existsById(id)) {
            log.error("Order not found with ID: {}", id);
            throw new OrderNotFoundException("Order not found with id: " + id);
        }

        orderRepository.deleteById(id);
        log.info("Order deleted successfully with ID: {}", id);
    }
}