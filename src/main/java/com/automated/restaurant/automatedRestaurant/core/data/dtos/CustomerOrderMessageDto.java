package com.automated.restaurant.automatedRestaurant.core.data.dtos;

import com.automated.restaurant.automatedRestaurant.core.data.responses.CustomerOrderResponse;
import lombok.Data;

@Data
public class CustomerOrderMessageDto {

    private String tableIdentification;

    private CustomerOrderResponse customerOrder;

    public CustomerOrderMessageDto(String tableIdentification, CustomerOrderResponse customerOrder) {
        this.tableIdentification = tableIdentification;
        this.customerOrder = customerOrder;
    }
}
