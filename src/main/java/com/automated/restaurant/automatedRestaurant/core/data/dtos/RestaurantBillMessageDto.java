package com.automated.restaurant.automatedRestaurant.core.data.dtos;

import com.automated.restaurant.automatedRestaurant.core.data.enums.RestaurantBillAction;
import com.automated.restaurant.automatedRestaurant.core.data.responses.BillResponse;
import lombok.Data;

@Data
public class RestaurantBillMessageDto {

    private RestaurantBillAction action;

    private BillResponse bill;

    public RestaurantBillMessageDto(RestaurantBillAction action, BillResponse bill) {
        this.action = action;
        this.bill = bill;
    }
}
