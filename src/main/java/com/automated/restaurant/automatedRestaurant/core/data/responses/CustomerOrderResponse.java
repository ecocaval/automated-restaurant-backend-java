package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.CustomerOrder;
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
public class CustomerOrderResponse {

    private UUID customerId;

    private List<ProductOrderInfoResponse> productOrderInfo;

    public static CustomerOrderResponse fromCustomerOrder(CustomerOrder customerOrder) {
        return CustomerOrderResponse.builder()
                .customerId(customerOrder.getCustomer().getId())
                .productOrderInfo(customerOrder.getProductOrderInfo().stream().map(ProductOrderInfoResponse::fromProductOrderInfo).toList())
                .build();
    }
}
