package com.automated.restaurant.automatedRestaurant.presentation.repositories;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Collaborator;
import com.automated.restaurant.automatedRestaurant.presentation.entities.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, UUID> {

    List<Collaborator> findByIdIn(Collection<UUID> collaboratorIds);

    Optional<Collaborator> findByEmail(String email);

    Optional<Collaborator> findByCpf(String cpf);
}
