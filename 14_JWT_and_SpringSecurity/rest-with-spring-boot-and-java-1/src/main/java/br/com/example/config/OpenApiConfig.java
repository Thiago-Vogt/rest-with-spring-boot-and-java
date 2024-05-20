package br.com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("RESTful API with Java and Spring Boot")
					.version("v1")
					.description("Learning about REST with Spring Boot")
					.termsOfService("https://pub.exempla.com.br/curso")
					.license(new License().name("Apache 2.0")
							.url("https://pub.exempla.com.br/curso")));
	}
}
