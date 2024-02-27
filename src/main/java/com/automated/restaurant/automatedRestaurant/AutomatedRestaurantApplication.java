package com.automated.restaurant.automatedRestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AutomatedRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomatedRestaurantApplication.class, args);
	}

}
