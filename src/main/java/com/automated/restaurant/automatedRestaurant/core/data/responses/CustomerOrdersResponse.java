package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Customer;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerOrderResponse {

    private CustomerResponse customer;

    private List<ProductOrderInfoResponse> productOrderInfo;

    public static CustomerOrderResponse fromCustomerOrder(Customer customer, List<Order> orderList) {
        return CustomerOrderResponse.builder()
                .customer(CustomerResponse.fromCustomer(customer))
                .productOrderInfo(orderList.stream().map(ProductOrderInfoResponse::fromProductOrderInfo).toList())
                .build();
    }
}
