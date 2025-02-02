package com.akentech.microservices.order;

import com.akentech.microservices.order.dto.OrderRequest;
import com.akentech.microservices.order.model.Order;
import com.akentech.microservices.order.repository.OrderRepository;
import com.akentech.microservices.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = TestcontainersConfiguration.class)
class OrderServiceApplicationTests {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@BeforeEach
	void setUp() {
		// Clear the database before each test
		orderRepository.deleteAll();
	}

	@Test
	void testPlaceOrder() {
		// Arrange
		OrderRequest orderRequest = new OrderRequest(null, "ORDER123", "iphone_15", BigDecimal.valueOf(1000.00), 1);

		// Act
		orderService.placeOrder(orderRequest);

		// Assert
		List<Order> orders = orderService.getAllOrders();
		assertEquals(1, orders.size());
		assertEquals("iphone_15", orders.get(0).getSkuCode());
	}

	@Test
	void testGetAllOrders() {
		// Arrange
		OrderRequest orderRequest1 = new OrderRequest(null, "ORDER123", "iphone_15", BigDecimal.valueOf(1000.00), 1);
		OrderRequest orderRequest2 = new OrderRequest(null, "ORDER124", "samsung_s23", BigDecimal.valueOf(900.00), 2);
		orderService.placeOrder(orderRequest1);
		orderService.placeOrder(orderRequest2);

		// Act
		List<Order> orders = orderService.getAllOrders();

		// Assert
		assertEquals(2, orders.size());
	}

	@Test
	void testGetOrderById() {
		// Arrange
		OrderRequest orderRequest = new OrderRequest(null, "ORDER123", "iphone_15", BigDecimal.valueOf(1000.00), 1);
		orderService.placeOrder(orderRequest);
		List<Order> orders = orderService.getAllOrders();
		Long orderId = orders.get(0).getId();

		// Act
		Optional<Order> foundOrder = orderService.getOrderById(orderId);

		// Assert
		assertTrue(foundOrder.isPresent());
		assertEquals("iphone_15", foundOrder.get().getSkuCode());
	}

	@Test
	void testUpdateOrder() {
		// Arrange
		OrderRequest orderRequest = new OrderRequest(null, "ORDER123", "iphone_15", BigDecimal.valueOf(1000.00), 1);
		orderService.placeOrder(orderRequest);
		List<Order> orders = orderService.getAllOrders();
		Long orderId = orders.get(0).getId();

		OrderRequest updatedOrderRequest = new OrderRequest(orderId, "ORDER123", "iphone_15_pro", BigDecimal.valueOf(1200.00), 2);

		// Act
		orderService.updateOrder(orderId, updatedOrderRequest);

		// Assert
		Optional<Order> updatedOrder = orderService.getOrderById(orderId);
		assertTrue(updatedOrder.isPresent());
		assertEquals("iphone_15_pro", updatedOrder.get().getSkuCode());
		assertEquals(0, updatedOrder.get().getPrice().compareTo(BigDecimal.valueOf(1200.00))); // Use compareTo
		assertEquals(2, updatedOrder.get().getQuantity());
	}
	@Test
	void testDeleteOrder() {
		// Arrange
		OrderRequest orderRequest = new OrderRequest(null, "ORDER123", "iphone_15", BigDecimal.valueOf(1000.00), 1);
		orderService.placeOrder(orderRequest);
		List<Order> orders = orderService.getAllOrders();
		Long orderId = orders.get(0).getId();

		// Act
		orderService.deleteOrder(orderId);

		// Assert
		Optional<Order> deletedOrder = orderService.getOrderById(orderId);
		assertFalse(deletedOrder.isPresent());
	}
}