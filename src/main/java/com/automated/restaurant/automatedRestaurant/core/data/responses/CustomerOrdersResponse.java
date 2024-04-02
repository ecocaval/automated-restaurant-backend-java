package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Customer;
import com.automated.restaurant.automatedRestaurant.presentation.entities.CustomerOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerOrdersResponse {

    private CustomerResponse customer;

    private List<CustomerOrderResponse> orders;

    public static CustomerOrdersResponse fromCustomerAndOrders(Customer customer, List<CustomerOrder> customerOrderList) {
        return CustomerOrdersResponse.builder()
                .customer(CustomerResponse.fromCustomer(customer))
                .orders(customerOrderList.stream().map(CustomerOrderResponse::fromOrder).toList())
                .build();
    }
}
