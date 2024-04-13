package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.*;
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
public class BillResponse {

    private UUID id;

    private LocalDateTime creationDate;

    private LocalDateTime lastModifiedDate;

    private boolean active;

    private List<CustomerOrderResponse> customerOrders;

    private List<CustomerResponse> customers;

    private RestaurantTableResponse restaurantTable;

    public static BillResponse fromBill(Bill bill) {

        List<CustomerOrderResponse> customerOrders = null;

        customerOrders = bill.getCustomerOrders() != null ?
                bill.getCustomerOrders().stream().map(CustomerOrderResponse::fromCustomerOrder).toList() :
                new ArrayList<>();

        return BillResponse.builder()
                .id(bill.getId())
                .creationDate(bill.getCreationDate())
                .lastModifiedDate(bill.getLastModifiedDate())
                .active(bill.isActive())
                .customerOrders(customerOrders)
                .restaurantTable(RestaurantTableResponse.fromRestaurantTable(bill.getRestaurantTable()))
                .customers(bill.getCustomers().stream().map(CustomerResponse::fromCustomer).toList())
                .build();
    }
}
