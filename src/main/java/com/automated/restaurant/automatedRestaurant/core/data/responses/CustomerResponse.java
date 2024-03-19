package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.BaseEntity;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Customer;
import com.automated.restaurant.automatedRestaurant.presentation.entities.CustomerOrder;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerResponse {

    private UUID id;

    private String name;

    private String email;

    private String cellPhoneAreaCode;

    private String cellPhone;

    private List<UUID> customerOrderIds;

    private List<UUID> restaurantIds;

    public static CustomerResponse fromCustomer(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .cellPhoneAreaCode(customer.getCellPhoneAreaCode())
                .cellPhone(customer.getCellPhone())
                .customerOrderIds(customer.getCustomerOrders() != null ? customer.getCustomerOrders().stream().map(BaseEntity::getId).toList() : null)
                .restaurantIds(customer.getRestaurants() != null ? customer.getRestaurants().stream().map(Restaurant::getId).toList() : null)
                .build();
    }
}
