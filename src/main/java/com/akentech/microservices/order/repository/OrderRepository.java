package com.akentech.microservices.order.repository;

import com.akentech.microservices.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
