package com.automated.restaurant.automatedRestaurant.core.data.responses;

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
public class CustomerOrderResponse {

    private CustomerResponse customer;

    private List<ProductOrderInfoResponse> productOrderInfo;

    public static CustomerOrderResponse fromCustomerOrder(CustomerOrder customerOrder) {
        return CustomerOrderResponse.builder()
                .customer(CustomerResponse.fromCustomer(customerOrder.getCustomer()))
                .productOrderInfo(customerOrder.getProductOrderInfo().stream().map(ProductOrderInfoResponse::fromProductOrderInfo).toList())
                .build();
    }
}
