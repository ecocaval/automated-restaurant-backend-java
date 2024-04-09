package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateJobTitleRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateJobTitleRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.JobTitle;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;

import java.util.List;
import java.util.UUID;

public interface JobTitleUseCase {

    JobTitle findById(UUID id);

    List<JobTitle> createAll(List<CreateJobTitleRequest> requests, Restaurant restaurant);

    List<JobTitle> updateAll(List<UpdateJobTitleRequest> requests, Restaurant restaurant);

    void deleteAll(List<UUID> jobTitleIds);
}
