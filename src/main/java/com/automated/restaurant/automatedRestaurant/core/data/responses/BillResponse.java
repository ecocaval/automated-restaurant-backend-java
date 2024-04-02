package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BillResponse {

    private UUID id;

    private boolean active;

    private List<CustomerOrdersResponse> customerOrders;

    private RestaurantTableResponse restaurantTable;

    public static BillResponse fromBill(Bill bill) {

        List<CustomerOrdersResponse> customerOrders = new ArrayList<>();

        bill.getCustomers().forEach(customer -> {
            customerOrders.add(
                    CustomerOrdersResponse.builder()
                            .customer(CustomerResponse.fromCustomer(customer))
                            .orders(bill.getCustomerOrders().stream()
                                    .filter(customerOrder -> customerOrder.getCustomer().getId().equals(customer.getId()))
                                    .map(CustomerOrderResponse::fromOrder)
                                    .toList())
                            .build()
            );
        });

        return BillResponse.builder()
                .id(bill.getId())
                .active(bill.isActive())
                .customerOrders(customerOrders)
                .restaurantTable(RestaurantTableResponse.fromRestaurantTable(bill.getRestaurantTable()))
                .build();
    }
}
