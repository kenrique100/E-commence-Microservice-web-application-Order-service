package com.akentech.microservices.order;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(initializers = TestcontainersConfiguration.class)
@SpringBootTest
class OrderServiceApplicationTests {

	@Test
	void contextLoads() {
	}
}