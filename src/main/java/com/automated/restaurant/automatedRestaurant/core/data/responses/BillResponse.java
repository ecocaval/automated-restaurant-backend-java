package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.*;
import jakarta.persistence.*;
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
public class BillResponse {

    private UUID id;

    private boolean active;

    private List<CustomerOrderResponse> customerOrders;

    private TableResponse restaurantTable;

    private List<CustomerResponse> customers;

    public static BillResponse fromBill(Bill bill) {
        return BillResponse.builder()
                .id(bill.getId())
                .active(bill.isActive())
                .customerOrders(bill.getCustomerOrders() != null ? bill.getCustomerOrders().stream().map(CustomerOrderResponse::fromCustomerOrder).toList() : null)
                .restaurantTable(TableResponse.fromRestaurantTable(bill.getRestaurantTable()))
                .customers(bill.getCustomers().stream().map(CustomerResponse::fromCustomer).toList())
                .build();
    }
}
