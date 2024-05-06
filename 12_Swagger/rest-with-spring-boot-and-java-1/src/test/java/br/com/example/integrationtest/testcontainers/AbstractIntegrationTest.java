package br.com.example.integrationtest.testcontainers;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		
		static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:11.1.4");
		
		private static void startContainers() {
			Startables.deepStart(Stream.of(mariadb)).join();
		}

		private static Map<String, String> createConnectionConfiguration() {
			return Map.of(
				"spring.datasource.url", mariadb.getJdbcUrl(),
				"spring.datasource.username", mariadb.getUsername(),
				"spring.datasource.password", mariadb.getPassword()
					);
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			startContainers();
			ConfigurableEnvironment enviroment = applicationContext.getEnvironment();
			MapPropertySource testcontainers = new MapPropertySource("testcontainers", (Map) createConnectionConfiguration());
			enviroment.getPropertySources().addFirst(testcontainers);
		}

	}
}
