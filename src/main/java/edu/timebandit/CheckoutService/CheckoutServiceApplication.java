package edu.timebandit.CheckoutService;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CheckoutServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckoutServiceApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new io.swagger.v3.oas.models.Components())
				.info(new io.swagger.v3.oas.models.info.Info()
						.title("Checkout Service API")
						.version("v1")
						.description("Checkout Service API for TimeBandit"));
	}

}
