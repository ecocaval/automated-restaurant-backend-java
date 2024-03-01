package com.automated.restaurant.automatedRestaurant.presentation.repositories;

import com.automated.restaurant.automatedRestaurant.presentation.entities.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, UUID> {

    List<JobTitle> findByIdIn(Collection<UUID> jobTitlesIds);

    Boolean existsByRestaurantIdAndName(UUID restaurantId, String name);
}
