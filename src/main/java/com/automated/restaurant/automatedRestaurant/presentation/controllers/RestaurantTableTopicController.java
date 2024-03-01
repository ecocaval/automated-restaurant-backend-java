package com.automated.restaurant.automatedRestaurant.presentation.controllers;

import com.automated.restaurant.automatedRestaurant.core.data.requests.OnTableUpdateRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.TableUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RestaurantTableTopicController {

    @Autowired
    private TableUseCase tableUseCase;

    @MessageMapping("/on-table-update")
    @SendTo("/restaurant-table-topic/update")
    public List<RestaurantTable> updateTable(OnTableUpdateRequest request) {

        var table = this.tableUseCase.findById(request.getId());

        this.tableUseCase.updateStatus(table, request);

        return this.tableUseCase.findAll();
    }
}
