package com.akentech.microservices.order.service;

import com.akentech.microservices.order.dto.OrderRequest;
import com.akentech.microservices.order.exception.OrderNotFoundException;
import com.akentech.microservices.order.model.Order;
import com.akentech.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        log.info("Placing order with SKU Code: {}", orderRequest.skuCode());

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());

        orderRepository.save(order);
        log.info("Order placed successfully with ID: {}", order.getId());
    }

    @Override
    public List<Order> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        log.info("Fetching order by ID: {}", id);
        return orderRepository.findById(id);
    }

    @Override
    public void updateOrder(Long id, OrderRequest orderRequest) {
        log.info("Updating order with ID: {}", id);

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found with ID: {}", id);
                    return new OrderNotFoundException("Order not found with id: " + id);
                });

        existingOrder.setPrice(orderRequest.price());
        existingOrder.setSkuCode(orderRequest.skuCode());
        existingOrder.setQuantity(orderRequest.quantity());

        orderRepository.save(existingOrder);
        log.info("Order updated successfully with ID: {}", id);
    }

    @Override
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