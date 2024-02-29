package com.automated.restaurant.automatedRestaurant.presentation.controllers;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateProductRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantTableRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateRestaurantRequest;
import com.automated.restaurant.automatedRestaurant.core.data.responses.ProductResponse;
import com.automated.restaurant.automatedRestaurant.core.data.responses.RestaurantResponse;
import com.automated.restaurant.automatedRestaurant.core.data.responses.RestaurantTableResponse;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantImage;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.ImageStoreUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.RestaurantProductUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.RestaurantTableUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.RestaurantUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantTableUseCase restaurantTableUseCase;

    @Autowired
    private RestaurantUseCase restaurantUseCase;

    @Autowired
    private RestaurantProductUseCase restaurantProductUseCase;

    @Autowired
    private ImageStoreUseCase imageStoreUseCase;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> findById(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        return ResponseEntity.ok(
                RestaurantResponse.fromRestaurant(this.restaurantUseCase.findById(restaurantId))
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

        return ResponseEntity.status(HttpStatus.OK).body(
                RestaurantResponse.fromRestaurant(
                        this.restaurantUseCase.update(request, restaurant)
                )
        );
    }

    @PostMapping("/{restaurantId}/image")
    public ResponseEntity<RestaurantImage> uploadRestaurantImage(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestParam("image") MultipartFile imageFile
    ) {
//        this.imageStoreUseCase.uploadRestaurantImage(
//                imageFile, this.restaurantUseCase.findById(restaurantId)
//        );

        return ResponseEntity.ok(
                this.imageStoreUseCase.uploadRestaurantImage(
                        imageFile, this.restaurantUseCase.findById(restaurantId)
                )
        );
    }

    @GetMapping("/table/{tableId}")
    public ResponseEntity<RestaurantTableResponse> findTableByTableId(
            @PathVariable("tableId") UUID tableId
    ) {
        return ResponseEntity.ok(
                RestaurantTableResponse.fromRestaurantTable(this.restaurantTableUseCase.findById(tableId))
        );
    }

    @GetMapping("/{restaurantId}/tables")
    public ResponseEntity<List<RestaurantTableResponse>> findTablesByRestaurantId(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        return ResponseEntity.ok(
                RestaurantResponse.fromRestaurant(this.restaurantUseCase.findById(restaurantId)).getTables()
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
    public ResponseEntity<List<ProductResponse>> createProducts(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody @Valid List<CreateProductRequest> requests
    ) {
        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.restaurantTableUseCase.createAllProducts(restaurant, requests)
                        .stream()
                        .map(ProductResponse::fromProduct)
                        .toList()
        );
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<?> uploadProductImage(
            @PathVariable("productId") UUID productId,
            @RequestParam("image") MultipartFile imageFile
    ) {
        this.imageStoreUseCase.uploadProductImage(
                imageFile, this.restaurantProductUseCase.findById(productId)
        );

        return ResponseEntity.ok().build();
    }


}
