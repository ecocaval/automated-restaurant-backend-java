package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.core.data.enums.CustomerOrderStatus;
import com.automated.restaurant.automatedRestaurant.presentation.entities.CustomerOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerOrderResponse {

    private UUID id;

    private LocalDateTime creationDate;

    private LocalDateTime lastModifiedDate;

    private CustomerResponse customer;

    private RestaurantTableResponse restaurantTable;

    private UUID billId;

    private CustomerOrderStatus status;

    private List<ProductOrderResponse> productOrders;

    public static CustomerOrderResponse fromCustomerOrder(CustomerOrder customerOrder) {
        return CustomerOrderResponse.builder()
                .id(customerOrder.getId())
                .creationDate(customerOrder.getCreationDate())
                .lastModifiedDate(customerOrder.getLastModifiedDate())
                .customer(CustomerResponse.fromCustomer(customerOrder.getCustomer()))
                .restaurantTable(RestaurantTableResponse.fromRestaurantTable(customerOrder.getBill().getRestaurantTable()))
                .billId(customerOrder.getBill().getId())
                .productOrders(
                    customerOrder.getProductOrders() != null ?
                        customerOrder.getProductOrders().stream().map(ProductOrderResponse::fromProductOrder).toList() :
                        new ArrayList<>()
                )
                .status(customerOrder.getStatus())
                .build();
    }
}
