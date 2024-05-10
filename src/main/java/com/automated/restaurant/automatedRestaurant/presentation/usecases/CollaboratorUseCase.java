package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateCollaboratorRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateCollaboratorRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Collaborator;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;

import java.util.List;
import java.util.UUID;

public interface CollaboratorUseCase {

    Collaborator findById(UUID collaboratorId);

    Collaborator findByEmail(String email);

    Collaborator findByCpf(String cpf);

    Collaborator findByLogin(String login);

    Collaborator create(CreateCollaboratorRequest request, Restaurant restaurant);

    Collaborator update(UpdateCollaboratorRequest request, Collaborator collaborator);

    void deleteAll(List<UUID> collaboratorIds);

}
