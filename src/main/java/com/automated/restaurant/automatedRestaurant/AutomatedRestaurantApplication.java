package com.automated.restaurant.automatedRestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@RestController
public class AutomatedRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomatedRestaurantApplication.class, args);
	}

	@GetMapping
	public String lifeSignal() {
		return "Hello world";
	}

}
