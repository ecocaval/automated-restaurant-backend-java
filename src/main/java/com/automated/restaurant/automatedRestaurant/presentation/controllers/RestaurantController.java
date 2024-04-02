package com.automated.restaurant.automatedRestaurant.presentation.controllers;

import com.automated.restaurant.automatedRestaurant.core.data.requests.*;
import com.automated.restaurant.automatedRestaurant.core.data.responses.*;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantImage;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

    @Autowired
    private JobTitleUseCase jobTitleUseCase;

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
        return ResponseEntity.ok(
                this.imageStoreUseCase.uploadRestaurantImage(
                        imageFile, this.restaurantUseCase.findById(restaurantId)
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
            @RequestBody @Valid List<CreateTableRequest> requests
    ) {
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
            @RequestBody @Valid List<UpdateTableRequest> requests
    ) {
        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.OK).body(
                this.restaurantTableUseCase.updateAll(requests, restaurant)
                        .stream()
                        .map(RestaurantTableResponse::fromRestaurantTable)
                        .toList()
        );
    }

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
            @RequestBody @Valid List<CreateProductRequest> requests
    ) {
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
            @RequestBody @Valid List<UpdateProductRequest> requests
    ) {
        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.OK).body(
                this.productUseCase.updateAll(requests, restaurant)
                        .stream()
                        .map(ProductResponse::fromProduct)
                        .toList()
        );
    }

    @DeleteMapping("/products/{productIds}")
    public ResponseEntity<?> deleteProducts(
            @PathVariable("productIds") List<UUID> productIds
    ) {
        this.productUseCase.deleteAll(productIds);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/product/{productId}/image")
    public ResponseEntity<?> uploadProductImage(
            @PathVariable("productId") UUID productId,
            @RequestParam("image") MultipartFile imageFile
    ) {
        this.imageStoreUseCase.uploadProductImage(
                imageFile, this.productUseCase.findById(productId)
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/job-title/{jobTitleId}")
    public ResponseEntity<JobTitleResponse> findJobTitleById(
            @PathVariable("jobTitleId") UUID jobTitleId
    ) {
        return ResponseEntity.ok(
                JobTitleResponse.fromJobTitle(this.jobTitleUseCase.findById(jobTitleId))
        );
    }

    @GetMapping("/{restaurantId}/job-titles")
    public ResponseEntity<List<JobTitleResponse>> findAllJobTitlesByRestaurantId(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        return ResponseEntity.ok(
                RestaurantResponse.fromRestaurant(this.restaurantUseCase.findById(restaurantId)).getJobTitles()
        );
    }

    @PostMapping("/{restaurantId}/job-titles")
    public ResponseEntity<List<JobTitleResponse>> createJobTitles(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody @Valid List<CreateJobTitleRequest> requests
    ) {
        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.jobTitleUseCase.createAll(requests, restaurant)
                        .stream()
                        .map(JobTitleResponse::fromJobTitle)
                        .toList()
        );
    }

    @PatchMapping("/{restaurantId}/job-titles")
    public ResponseEntity<List<JobTitleResponse>> updateJobTitles(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody @Valid List<UpdateJobTitleRequest> requests
    ) {
        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.OK).body(
                this.jobTitleUseCase.updateAll(requests, restaurant)
                        .stream()
                        .map(JobTitleResponse::fromJobTitle)
                        .toList()
        );
    }

    @DeleteMapping("/job-titles/{jobTitleIds}")
    public ResponseEntity<?> deleteJobTitles(
            @PathVariable("{jobTitleIds}") List<UUID> jobTitleIds
    ) {
        this.jobTitleUseCase.deleteAll(jobTitleIds);

        return ResponseEntity.ok().build();
    }

}
