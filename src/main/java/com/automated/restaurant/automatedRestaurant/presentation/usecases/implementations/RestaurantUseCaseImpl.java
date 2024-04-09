package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateCollaboratorRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateRestaurantRequest;
import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.core.utils.CpfUtils;
import com.automated.restaurant.automatedRestaurant.core.utils.EmailUtils;
import com.automated.restaurant.automatedRestaurant.core.utils.JobTitleUtils;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Collaborator;
import com.automated.restaurant.automatedRestaurant.presentation.entities.JobTitle;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.*;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.BadRequestException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.*;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.CollaboratorUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.RestaurantUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantUseCaseImpl implements RestaurantUseCase {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private JobTitleRepository jobTitleRepository;

    @Autowired
    private CollaboratorUseCase collaboratorUseCase;

    @Override
    public Restaurant findById(UUID id) {
        return this.restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    @Override
    public Restaurant create(CreateRestaurantRequest request) {

        if(request.getCollaborator() == null) {
            throw new BadRequestException(ErrorMessages.ERROR_NULL_COLLABORATOR_WHEN_CREATING_RESTAURANT.getMessage());
        }

        var restaurant = this.restaurantRepository.save(
                Restaurant.fromCreateRequest(request)
        );

        var jobTitle = this.jobTitleRepository.save(
                new JobTitle(JobTitleUtils.OWNER_JOB_TITLE, restaurant)
        );

        validateCollaboratorRequest(request.getCollaborator());

        restaurant.setCollaborators(
                List.of(this.collaboratorUseCase.create(request.getCollaborator(), restaurant))
        );

        restaurant.setJobTitles(List.of(jobTitle));

        return restaurant;
    }

    @Override
    public Restaurant update(UpdateRestaurantRequest request, Restaurant restaurant) {

        updateRestaurantName(request, restaurant);

        return this.restaurantRepository.save(restaurant);
    }

    private void updateRestaurantName(UpdateRestaurantRequest request, Restaurant restaurant) {
        if (request.getName() != null) {
            restaurant.setName(request.getName());
        }
    }

    private void validateCollaboratorRequest(CreateCollaboratorRequest request) {

        if(!(request.getName() != null && !request.getName().isBlank() && !request.getName().isEmpty())) {
            throw new BadRequestException(ErrorMessages.ERROR_COLLABORATOR_NAME_IS_NULL_ON_CREATE_REQUEST.getMessage());
        }

        if(!(request.getPassword() != null && !request.getPassword().isBlank() && !request.getPassword().isEmpty())) {
            throw new BadRequestException(ErrorMessages.ERROR_COLLABORATOR_PASSWORD_IS_NULL_ON_CREATE_REQUEST.getMessage());
        }

        if(!(request.getCpf() != null && CpfUtils.stringIsAValidCpf(request.getCpf()))) {
            throw new BadRequestException(ErrorMessages.ERROR_INVALID_CPF.getMessage());
        }

        if(!(request.getEmail() != null && EmailUtils.stringIsAValidEmail(request.getEmail()))) {
            throw new BadRequestException(ErrorMessages.ERROR_INVALID_EMAIL.getMessage());
        }
    }
}
