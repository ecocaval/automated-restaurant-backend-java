package com.automated.restaurant.automatedRestaurant.presentation.controllers;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateJobTitleRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateJobTitleRequest;
import com.automated.restaurant.automatedRestaurant.core.data.responses.JobTitleResponse;
import com.automated.restaurant.automatedRestaurant.core.data.responses.RestaurantResponse;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.JobTitleUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.RestaurantUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/job-title")
public class JobTitleController {

    @Autowired
    private RestaurantUseCase restaurantUseCase;

    @Autowired
    private JobTitleUseCase jobTitleUseCase;

    @GetMapping("{jobTitleId}")
    public ResponseEntity<JobTitleResponse> findById(
            @PathVariable("jobTitleId") UUID jobTitleId
    ) {
        return ResponseEntity.ok(
                JobTitleResponse.fromJobTitle(this.jobTitleUseCase.findById(jobTitleId))
        );
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<JobTitleResponse>> findAllByRestaurantId(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody @Valid List<CreateJobTitleRequest> requests
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                RestaurantResponse.fromRestaurant(this.restaurantUseCase.findById(restaurantId)).getJobTitles()
        );
    }

    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<JobTitleResponse>> createAll(
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

    @PatchMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<JobTitleResponse>> updateAll(
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

    @DeleteMapping("/{jobTitleIds}")
    public ResponseEntity<?> deleteAll(
            @PathVariable("{jobTitleIds}") List<UUID> jobTitleIds
    ) {
        this.jobTitleUseCase.deleteAll(jobTitleIds);

        return ResponseEntity.ok().build();
    }
}
