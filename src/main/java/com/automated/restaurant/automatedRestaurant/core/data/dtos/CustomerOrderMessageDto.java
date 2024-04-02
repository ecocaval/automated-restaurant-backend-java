package com.automated.restaurant.automatedRestaurant.core.data.dtos;

import com.automated.restaurant.automatedRestaurant.core.data.responses.CustomerOrdersResponse;
import lombok.Data;

@Data
public class CustomerOrderMessageDto {

    private String tableIdentification;

    private CustomerOrdersResponse customerOrder;

    public CustomerOrderMessageDto(String tableIdentification, CustomerOrdersResponse customerOrder) {
        this.tableIdentification = tableIdentification;
        this.customerOrder = customerOrder;
    }
}
