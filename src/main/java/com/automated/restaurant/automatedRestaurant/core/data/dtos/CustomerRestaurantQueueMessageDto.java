package com.automated.restaurant.automatedRestaurant.core.data.dtos;

import com.automated.restaurant.automatedRestaurant.core.data.enums.RestaurantQueueAction;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class CustomerRestaurantQueueMessageDto {

    private RestaurantQueueAction action;

    private CustomerDto customer;

    public CustomerRestaurantQueueMessageDto(RestaurantQueueAction action, Customer customer) {
        this.action = action;
        this.customer = this.fromCustomer(customer);
    }

    @AllArgsConstructor
    @Builder
    @Data
    public static class CustomerDto {

        private UUID id;

        private String name;

        private String email;

        private String cellPhoneAreaCode;

        private String cellPhone;
    }

    private CustomerDto fromCustomer(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .cellPhoneAreaCode(customer.getCellPhoneAreaCode())
                .cellPhone(customer.getCellPhone())
                .build();
    }
}
