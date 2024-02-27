package com.automated.restaurant.automatedRestaurant.presentation.controllers;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantTableRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateRestaurantRequest;
import com.automated.restaurant.automatedRestaurant.core.data.responses.RestaurantResponse;
import com.automated.restaurant.automatedRestaurant.core.data.responses.RestaurantTableResponse;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.RestaurantTableUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.RestaurantUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantTableUseCase restaurantTableUseCase;

    @Autowired
    private RestaurantUseCase restaurantUseCase;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> findById(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        return ResponseEntity.ok(
                RestaurantResponse.fromRestaurant(this.restaurantUseCase.findById(restaurantId))
        );
    }

    @GetMapping("/{restaurantId}/tables")
    public ResponseEntity<List<RestaurantTableResponse>> findByTablesByRestaurantId(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        return ResponseEntity.ok(
                RestaurantResponse.fromRestaurant(this.restaurantUseCase.findById(restaurantId)).getTables()
        );
    }


    @PostMapping
    public ResponseEntity<RestaurantResponse> create(
            @RequestBody @Valid CreateRestaurantRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            RestaurantResponse.fromRestaurant(
                    this.restaurantUseCase.create(request)
            )
        );
    }

    @PatchMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody UpdateRestaurantRequest request
    ) {
        var restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestaurantResponse.fromRestaurant(
                        this.restaurantUseCase.update(request, restaurant)
                )
        );
    }

    @PostMapping("/{restaurantId}/tables")
    public ResponseEntity<List<RestaurantTableResponse>> createTables(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody @Valid List<CreateRestaurantTableRequest> requests
    ) {
        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.restaurantTableUseCase.createAll(restaurant, requests)
                        .stream()
                        .map(RestaurantTableResponse::fromRestaurantTable)
                        .toList()
        );
    }

    @PostMapping("/{restaurantId}/products")
    public ResponseEntity<List<RestaurantTableResponse>> createProducts(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody @Valid List<CreateRestaurantTableRequest> requests
    ) {
        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.restaurantTableUseCase.createAll(restaurant, requests)
                        .stream()
                        .map(RestaurantTableResponse::fromRestaurantTable)
                        .toList()
        );
    }

}
