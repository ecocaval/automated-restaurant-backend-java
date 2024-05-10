package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.enums.LoginIdentification;
import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateCollaboratorRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateCollaboratorRequest;
import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.core.utils.AsciiUtils;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Collaborator;
import com.automated.restaurant.automatedRestaurant.presentation.entities.JobTitle;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.CollaboratorNotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.CollaboratorRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.CollaboratorUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.JobTitleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CollaboratorUseCaseImpl implements CollaboratorUseCase {

    @Autowired
    private JobTitleUseCase jobTitleUseCase;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Collaborator findById(UUID collaboratorId) {
        return collaboratorRepository.findById(collaboratorId)
                .orElseThrow(() -> new CollaboratorNotFoundException(collaboratorId));
    }

    @Override
    public Collaborator findByEmail(String email) {
        return this.collaboratorRepository.findByEmail(email)
                .orElseThrow(() -> new CollaboratorNotFoundException(ErrorMessages.ERROR_COLLABORATOR_NOT_FOUND_BY_EMAIL.getMessage()));
    }

    @Override
    public Collaborator findByCpf(String cpf) {
        return this.collaboratorRepository.findByCpf(AsciiUtils.cleanDocumentString(cpf))
                .orElseThrow(() -> new CollaboratorNotFoundException(ErrorMessages.ERROR_COLLABORATOR_NOT_FOUND_BY_CPF.getMessage()));
    }

    @Override
    public Collaborator findByLogin(String login) {
        return switch (Objects.requireNonNull(LoginIdentification.getFromLogin(login))) {
            case EMAIL -> this.findByEmail(login);
            case CPF -> this.findByCpf(login);
        };
    }

    @Override
    public Collaborator create(CreateCollaboratorRequest request, Restaurant restaurant) {

        JobTitle jobTitle = null;

        if(request.getJobTitleId() != null) {
            jobTitle = this.jobTitleUseCase.findById(request.getJobTitleId());
        }

        Collaborator collaborator = jobTitle != null ?
                Collaborator.fromCreateRequest(request, restaurant, jobTitle) :
                Collaborator.fromCreateRequest(request, restaurant);

        collaborator.setPassword(passwordEncoder.encode(collaborator.getPassword()));

        return this.collaboratorRepository.save(collaborator);
    }

    @Override
    public Collaborator update(UpdateCollaboratorRequest request, Collaborator collaborator) {

        Optional.ofNullable(request.getName()).ifPresent(collaborator::setName);
        Optional.ofNullable(request.getIsAdmin()).ifPresent(collaborator::setIsAdmin);

        if(request.getJobTitleId() != null) {
            collaborator.setJobTitle(this.jobTitleUseCase.findById(request.getJobTitleId()));
        }

        return this.collaboratorRepository.save(collaborator);
    }

    @Override
    public void deleteAll(List<UUID> collaboratorIds) {
        this.collaboratorRepository.deleteAll(
                this.collaboratorRepository.findByIdIn(collaboratorIds)
        );
    }
}
