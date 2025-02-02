package com.akentech.microservices.order;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestcontainersConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final MariaDBContainer<?> mariaDBContainer;

	static {
		mariaDBContainer = new MariaDBContainer<>("mariadb:10.6")
				.withDatabaseName("testdb")
				.withUsername("testuser")
				.withPassword("testpass");
		mariaDBContainer.start(); // Start the container explicitly
	}

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		// Inject Testcontainers properties into the Spring context
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
				applicationContext,
				"spring.datasource.url=" + mariaDBContainer.getJdbcUrl(),
				"spring.datasource.username=" + mariaDBContainer.getUsername(),
				"spring.datasource.password=" + mariaDBContainer.getPassword()
		);
	}
}