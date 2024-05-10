package com.automated.restaurant.automatedRestaurant.presentation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@SQLDelete(sql = "UPDATE refresh_token SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class RefreshToken extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Collaborator collaborator;

    public static RefreshToken fromCollaborator(Collaborator collaborator) {
        return RefreshToken.builder()
                .collaborator(collaborator)
                .build();
    }

}
