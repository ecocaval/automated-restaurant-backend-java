package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static CustomerResponse fromCustomer(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .cellPhoneAreaCode(customer.getCellPhoneAreaCode())
                .cellPhone(customer.getCellPhone())
                .build();
    }
}
