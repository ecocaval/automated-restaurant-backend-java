package com.automated.restaurant.automatedRestaurant.presentation.controllers;

import com.automated.restaurant.automatedRestaurant.core.data.requests.*;
import com.automated.restaurant.automatedRestaurant.core.data.responses.*;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/collaborator")
public class CollaboratorController {

    @Autowired
    private RestaurantUseCase restaurantUseCase;

    @Autowired
    private CollaboratorUseCase collaboratorUseCase;

    @GetMapping("/{collaboratorId}")
    public ResponseEntity<CollaboratorResponse> findById(
            @PathVariable("collaboratorId") UUID collaboratorId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                CollaboratorResponse.fromCollaborator(this.collaboratorUseCase.findById(collaboratorId))
        );
    }

    @PatchMapping("/{collaboratorId}")
    public ResponseEntity<CollaboratorResponse> updateById(
            @PathVariable("collaboratorId") UUID collaboratorId,
            @RequestBody @Valid UpdateCollaboratorRequest request
    ) {
        var collaborator = this.collaboratorUseCase.findById(collaboratorId);

        return ResponseEntity.status(HttpStatus.OK).body(
                CollaboratorResponse.fromCollaborator(this.collaboratorUseCase.update(request, collaborator))
        );
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<CollaboratorResponse>> findAllCollaboratorByRestaurantId(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.restaurantUseCase.findById(restaurantId).getCollaborators()
                        .stream()
                        .map(CollaboratorResponse::fromCollaborator)
                        .toList()
        );
    }

    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<CollaboratorResponse> createCollaborator(
            @RequestBody @Valid CreateCollaboratorRequest request,
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        Restaurant restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                CollaboratorResponse.fromCollaborator(this.collaboratorUseCase.create(request, restaurant))
        );
    }

    @DeleteMapping("/{collaboratorIds}")
    public ResponseEntity<?> deleteAll(
            @PathVariable("collaboratorIds") List<UUID> collaboratorIds
    ) {
        this.collaboratorUseCase.deleteAll(collaboratorIds);
        return ResponseEntity.ok().build();
    }

}
