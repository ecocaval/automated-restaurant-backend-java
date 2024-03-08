package com.automated.restaurant.automatedRestaurant.presentation.controllers;

import com.automated.restaurant.automatedRestaurant.core.data.requests.TableStatusUpdateRequest;
import com.automated.restaurant.automatedRestaurant.core.data.responses.TableResponse;
import com.automated.restaurant.automatedRestaurant.core.utils.WebSocketUtils;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.TableUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class RestaurantTableTopicController {

    @Autowired
    private TableUseCase tableUseCase;

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("restaurant/{restaurantId}/table/update-status")
    public void updateTable(
            SimpMessageHeaderAccessor headerAccessor,
            @Payload TableStatusUpdateRequest request
    ) {
        System.out.println("Received message with id " + request.getId());

        UUID restaurantId = WebSocketUtils.getIdSectionFromUrl(headerAccessor, 3);

        RestaurantTable updatedTable = this.tableUseCase.updateStatus(
                this.tableUseCase.findById(request.getId()),
                request
        );

        this.template.convertAndSend(
                String.format("/app/restaurant/%s/table/update-status", restaurantId),
                TableResponse.fromRestaurantTable(updatedTable)
        );
    }
}

