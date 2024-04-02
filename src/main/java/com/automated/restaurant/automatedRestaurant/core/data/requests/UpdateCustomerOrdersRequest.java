package com.automated.restaurant.automatedRestaurant.core.data.requests;

import com.automated.restaurant.automatedRestaurant.core.data.enums.CustomerOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateCustomerOrdersRequest {

    @NotNull(message = "O id do cliente ao fazer o pedido não pode ser nulo.")
    private UUID customerId;

    private List<OrderInformation> orderInformation;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class OrderInformation {

        @NotNull(message = "O id do produto ao fazer o pedido não pode ser nulo.")
        private UUID orderId;

        private Long quantity;

        private CustomerOrderStatus status;
    }
}
