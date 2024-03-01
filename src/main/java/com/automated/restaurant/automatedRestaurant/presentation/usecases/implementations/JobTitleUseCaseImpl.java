package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateJobTitleRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateJobTitleRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.JobTitle;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.DuplicatedJobTitleException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.JobTitleNotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.JobTitleRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.JobTitleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobTitleUseCaseImpl implements JobTitleUseCase {

    @Autowired
    private JobTitleRepository restaurantJobTitleRepository;

    @Override
    public JobTitle findById(UUID id) {
        return this.restaurantJobTitleRepository.findById(id)
                .orElseThrow(() -> new JobTitleNotFoundException(id));
    }

    @Override
    public List<JobTitle> createAll(List<CreateJobTitleRequest> requests, Restaurant restaurant) {
        List<JobTitle> jobTitles = new ArrayList<>();

        for (CreateJobTitleRequest request : requests) {
            jobTitles.add(
                    this.restaurantJobTitleRepository.save(JobTitle.fromCreateRequest(request, restaurant))
            );
        }

        return jobTitles;
    }

    @Override
    public List<JobTitle> updateAll(List<UpdateJobTitleRequest> requests, Restaurant restaurant) {

        for (UpdateJobTitleRequest request : requests) {

            for (JobTitle jobTitle : restaurant.getJobTitles()) {

                if (jobTitle.getId().equals(request.getId())) {
                    validateDuplicityOnJobTitleByName(restaurant.getId(), request.getName());
                    Optional.ofNullable(request.getName()).ifPresent(jobTitle::setName);
                }
            }
        }

        return this.restaurantJobTitleRepository.saveAll(restaurant.getJobTitles());
    }

    @Override
    public void deleteAll(List<UUID> jobTitleIds) {
        this.restaurantJobTitleRepository.deleteAll(
                this.restaurantJobTitleRepository.findByIdIn(jobTitleIds)
        );
    }

    private void validateDuplicityOnJobTitleByName(UUID restaurantId, String name) {
        if (this.restaurantJobTitleRepository.existsByRestaurantIdAndName(restaurantId, name)) {
            throw new DuplicatedJobTitleException(name);
        }
    }
}
