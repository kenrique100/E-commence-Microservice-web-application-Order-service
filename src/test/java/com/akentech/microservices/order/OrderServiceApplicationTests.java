//package com.akentech.microservices.order;
//
//import com.akentech.microservices.order.dto.OrderRequest;
//import com.akentech.microservices.order.dto.OrderResponse;
//import com.akentech.microservices.order.repository.OrderRepository;
//import com.akentech.microservices.order.service.OrderService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Testcontainers
//@ContextConfiguration(initializers = TestcontainersConfiguration.class)
//class OrderServiceApplicationTests {
//
//	@Autowired
//	private OrderService orderService;
//
//	@Autowired
//	private OrderRepository orderRepository;
//
//	@BeforeEach
//	void setUp() {
//		// Clear the database before each test
//		orderRepository.deleteAll();
//	}
//
//	@Test
//	void testPlaceOrder() {
//		// Arrange
//		OrderRequest orderRequest = new OrderRequest(null, "ORDER123", "iphone_15", BigDecimal.valueOf(1000.00), 1, "product-123", "inventory-123");
//
//		// Act
//		orderService.placeOrder(orderRequest);
//
//		// Assert
//		List<OrderResponse> orders = orderService.getAllOrders();
//		assertEquals(1, orders.size());
//		assertEquals("iphone_15", orders.getFirst().skuCode());
//	}
//
//	@Test
//	void testGetAllOrders() {
//		// Arrange
//		OrderRequest orderRequest1 = new OrderRequest(null, "ORDER123", "iphone_15", BigDecimal.valueOf(1000.00), 1, "product-123", "inventory-123");
//		OrderRequest orderRequest2 = new OrderRequest(null, "ORDER124", "samsung_s23", BigDecimal.valueOf(900.00), 2, "product-456", "inventory-456");
//		orderService.placeOrder(orderRequest1);
//		orderService.placeOrder(orderRequest2);
//
//		// Act
//		List<OrderResponse> orders = orderService.getAllOrders();
//
//		// Assert
//		assertEquals(2, orders.size());
//	}
//
//	@Test
//	void testGetOrderById() {
//		// Arrange
//		OrderRequest orderRequest = new OrderRequest(null, "ORDER123", "iphone_15", BigDecimal.valueOf(1000.00), 1, "product-123", "inventory-123");
//		orderService.placeOrder(orderRequest);
//		List<OrderResponse> orders = orderService.getAllOrders();
//		Long orderId = orders.getFirst().id();
//
//		// Act
//		Optional<OrderResponse> foundOrder = orderService.getOrderById(orderId);
//
//		// Assert
//		assertTrue(foundOrder.isPresent());
//		assertEquals("iphone_15", foundOrder.get().skuCode());
//	}
//
//	@Test
//	void testUpdateOrder() {
//		// Arrange
//		OrderRequest orderRequest = new OrderRequest(null, "ORDER123", "iphone_15", BigDecimal.valueOf(1000.00), 1, "product-123", "inventory-123");
//		orderService.placeOrder(orderRequest);
//		List<OrderResponse> orders = orderService.getAllOrders();
//		Long orderId = orders.getFirst().id();
//
//		OrderRequest updatedOrderRequest = new OrderRequest(orderId, "ORDER123", "iphone_15_pro", BigDecimal.valueOf(1200.00), 2, "product-123", "inventory-123");
//
//		// Act
//		orderService.updateOrder(orderId, updatedOrderRequest);
//
//		// Assert
//		Optional<OrderResponse> updatedOrder = orderService.getOrderById(orderId);
//		assertTrue(updatedOrder.isPresent());
//		assertEquals("iphone_15_pro", updatedOrder.get().skuCode());
//		assertEquals(0, updatedOrder.get().price().compareTo(BigDecimal.valueOf(1200.00))); // Use compareTo
//		assertEquals(2, updatedOrder.get().quantity());
//	}
//
//	@Test
//	void testDeleteOrder() {
//		// Arrange
//		OrderRequest orderRequest = new OrderRequest(null, "ORDER123", "iphone_15", BigDecimal.valueOf(1000.00), 1, "product-123", "inventory-123");
//		orderService.placeOrder(orderRequest);
//		List<OrderResponse> orders = orderService.getAllOrders();
//		Long orderId = orders.getFirst().id();
//
//		// Act
//		orderService.deleteOrder(orderId);
//
//		// Assert
//		Optional<OrderResponse> deletedOrder = orderService.getOrderById(orderId);
//		assertFalse(deletedOrder.isPresent());
//	}
//}