package com.akentech.microservices.order;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestcontainersConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final MariaDBContainer<?> mariaDBContainer = new MariaDBContainer<>("mariadb:10.6")
			.withDatabaseName("testdb")
			.withUsername("testuser")
			.withPassword("testpass");

	static {
		mariaDBContainer.start();
		Runtime.getRuntime().addShutdownHook(new Thread(mariaDBContainer::stop));
	}

	@Override
	public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
				applicationContext,
				"spring.datasource.url=" + mariaDBContainer.getJdbcUrl(),
				"spring.datasource.username=" + mariaDBContainer.getUsername(),
				"spring.datasource.password=" + mariaDBContainer.getPassword()
		);
	}
}
