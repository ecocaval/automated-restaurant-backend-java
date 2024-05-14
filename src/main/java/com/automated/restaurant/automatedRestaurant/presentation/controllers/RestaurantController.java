package com.automated.restaurant.automatedRestaurant.presentation.controllers;

import com.automated.restaurant.automatedRestaurant.core.data.requests.*;
import com.automated.restaurant.automatedRestaurant.core.data.responses.*;
import com.automated.restaurant.automatedRestaurant.core.infra.security.JwtUtils;
import com.automated.restaurant.automatedRestaurant.presentation.entities.ProductCategory;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantUseCase restaurantUseCase;

    @Autowired
    private RestaurantTableUseCase restaurantTableUseCase;

    @Autowired
    private ProductUseCase productUseCase;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> findById(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        return ResponseEntity.ok(
                RestaurantResponse.fromRestaurant(this.restaurantUseCase.findById(restaurantId))
        );
    }

    @GetMapping("/{restaurantId}/queue")
    public ResponseEntity<List<CustomerResponse>> findAllCustomerInRestaurantQueueId(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        var restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.ok(
                restaurant.getRestaurantQueue().getCustomers() != null ?
                        restaurant.getRestaurantQueue().getCustomers().stream().map(CustomerResponse::fromCustomer).toList() :
                        new ArrayList<>()
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
        JwtUtils.validateAdminOrRestaurantCollaborator(restaurantId.toString());

        var restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.OK).body(
                RestaurantResponse.fromRestaurant(
                        this.restaurantUseCase.update(request, restaurant)
                )
        );
    }

    @GetMapping("/table/{tableId}")
    public ResponseEntity<RestaurantTableResponse> findTableById(
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
            @RequestBody List<CreateTableRequest> requests
    ) {
        JwtUtils.validateAdminOrRestaurantCollaborator(restaurantId.toString());

        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.restaurantTableUseCase.createAll(restaurant, requests)
                        .stream()
                        .map(RestaurantTableResponse::fromRestaurantTable)
                        .toList()
        );
    }

    @PatchMapping("/{restaurantId}/tables")
    public ResponseEntity<List<RestaurantTableResponse>> updateTables(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody List<UpdateTableRequest> requests
    ) {
        JwtUtils.validateAdminOrRestaurantCollaborator(restaurantId.toString());

        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.OK).body(
                this.restaurantTableUseCase.updateAll(requests, restaurant)
                        .stream()
                        .map(RestaurantTableResponse::fromRestaurantTable)
                        .toList()
        );
    }

    // FIXME: SHOULD BE AUTHENTICATED
    @DeleteMapping("/tables/{tableIds}")
    public ResponseEntity<?> deleteTables(
            @PathVariable("tableIds") List<UUID> tableIds
    ) {
        this.restaurantTableUseCase.deleteAll(tableIds);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductResponse> findProductById(
            @PathVariable("productId") UUID productId
    ) {
        return ResponseEntity.ok(
                ProductResponse.fromProduct(this.productUseCase.findById(productId))
        );
    }

    @GetMapping("/{restaurantId}/products")
    public ResponseEntity<List<ProductResponse>> findProductsByRestaurantId(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        return ResponseEntity.ok(
                RestaurantResponse.fromRestaurant(this.restaurantUseCase.findById(restaurantId)).getProducts()
        );
    }

    @PostMapping("/{restaurantId}/products")
    public ResponseEntity<List<ProductResponse>> createProducts(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody List<CreateProductRequest> requests
    ) {
        JwtUtils.validateAdminOrRestaurantCollaborator(restaurantId.toString());

        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.productUseCase.createAll(restaurant, requests)
                        .stream()
                        .map(ProductResponse::fromProduct)
                        .toList()
        );
    }

    @PatchMapping("/{restaurantId}/products")
    public ResponseEntity<List<ProductResponse>> updateProducts(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody List<UpdateProductRequest> requests
    ) {
        JwtUtils.validateAdminOrRestaurantCollaborator(restaurantId.toString());

        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.OK).body(
                this.productUseCase.updateAll(requests, restaurant)
                        .stream()
                        .map(ProductResponse::fromProduct)
                        .toList()
        );
    }

    // FIXME: SHOULD BE AUTHENTICATED
    @DeleteMapping("/products/{productIds}")
    public ResponseEntity<?> deleteProducts(
            @PathVariable("productIds") List<UUID> productIds
    ) {
        this.productUseCase.deleteAll(productIds);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{restaurantId}/product/category")
    public ResponseEntity<List<ProductCategoryResponse>> findAllProductCategories(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        JwtUtils.validateAdminOrRestaurantCollaborator(restaurantId.toString());

        var restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.OK).body(
                this.productUseCase.findAllProductCategoriesByRestaurant(restaurant)
                        .stream()
                        .map(ProductCategoryResponse::fromProductCategory)
                        .toList()
        );
    }

    @PostMapping("/{restaurantId}/product/category")
    public ResponseEntity<ProductCategoryResponse> createProductCategory(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody @Valid CreateProductCategoryRequest request
    ) {
        JwtUtils.validateAdminOrRestaurantCollaborator(restaurantId.toString());

        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        ProductCategory createdProductCategory = this.productUseCase.createProductCategory(
            restaurant, request
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ProductCategoryResponse.fromProductCategory(createdProductCategory)
        );
    }

    // FIXME: SHOULD BE AUTHENTICATED
    @DeleteMapping("/product/category/{categoriesIds}")
    public ResponseEntity<?> deleteProductCategories(
            @PathVariable("categoriesIds") List<UUID> categoriesIds
    ) {
        this.productUseCase.deleteAllProductCategories(categoriesIds);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
