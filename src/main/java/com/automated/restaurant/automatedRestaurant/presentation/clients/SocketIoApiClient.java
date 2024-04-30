package com.automated.restaurant.automatedRestaurant.presentation.clients;

import com.automated.restaurant.automatedRestaurant.core.data.dtos.CustomerOrderMessageDto;
import com.automated.restaurant.automatedRestaurant.core.data.dtos.CustomerRestaurantQueueMessageDto;
import com.automated.restaurant.automatedRestaurant.core.data.dtos.RestaurantBillMessageDto;
import com.automated.restaurant.automatedRestaurant.core.data.responses.RestaurantTableResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "socket-io-api", url = "${socket-io-api.url}")
public interface SocketIoApiClient {

    @PostMapping(value = "customer/restaurant/{restaurantId}/queue")
    void publishCustomerQueueEvent(
            CustomerRestaurantQueueMessageDto customerRestaurantQueueMessageDto,
            @PathVariable("restaurantId") String restaurantId
    );

    @PostMapping(value = "restaurant/{restaurantId}/bill")
    void publishBillEvent(
            RestaurantBillMessageDto restaurantBillMessageDto,
            @PathVariable("restaurantId") String restaurantId
    );

    @PostMapping(value = "restaurant/{restaurantId}/order")
    void publishOrderEvent(
            CustomerOrderMessageDto customerOrderMessageDto,
            @PathVariable("restaurantId") String restaurantId
    );

    @PostMapping(value = "restaurant/{restaurantId}/table")
    void publishTableEvent(
            RestaurantTableResponse restaurantTableResponse,
            @PathVariable("restaurantId") String restaurantId
    );
}
