package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateCollaboratorRequest;
import com.automated.restaurant.automatedRestaurant.core.utils.AsciiUtils;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@SQLDelete(sql = "UPDATE collaborator SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Collaborator extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isOwner = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isAdmin = false;

    @ManyToOne
    @JoinColumn(name = "job_title_id")
    private JobTitle jobTitle;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "collaborator_roles",
            joinColumns = @JoinColumn(name = "collaborator_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    public static Collaborator fromCreateRequest(CreateCollaboratorRequest request, Restaurant restaurant) {

        var isOwner = restaurant.getCollaborators() == null;

        return Collaborator.builder()
                .name(AsciiUtils.cleanString(request.getName()))
                .password(request.getPassword())
                .cpf(AsciiUtils.cleanDocumentString(request.getCpf()))
                .email(AsciiUtils.cleanString(request.getEmail()))
                .isOwner(isOwner)
                .isAdmin(isOwner || (request.getIsAdmin() != null ? request.getIsAdmin() : Boolean.FALSE))
                .restaurant(restaurant)
                .build();
    }

    public static Collaborator fromCreateRequest(CreateCollaboratorRequest request, Restaurant restaurant, JobTitle jobTitle) {

        var isOwner = restaurant.getCollaborators() == null;

        return Collaborator.builder()
                .name(AsciiUtils.cleanString(request.getName()))
                .password(request.getPassword())
                .cpf(AsciiUtils.cleanDocumentString(request.getCpf()))
                .email(AsciiUtils.cleanString(request.getEmail()))
                .isOwner(isOwner)
                .isAdmin(isOwner || (request.getIsAdmin() != null ? request.getIsAdmin() : Boolean.FALSE))
                .restaurant(restaurant)
                .jobTitle(jobTitle)
                .build();
    }
}
