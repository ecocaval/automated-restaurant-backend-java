package com.automated.restaurant.automatedRestaurant.core.infra.security;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Role;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.CollaboratorUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CollaboratorUseCase collaboratorUseCase;

    // @Lazy annotation used for breaking the cycle between securityConfig,
    // and accountUseCase, removing it will break the application!
    @Autowired
    public CustomUserDetailsService(@Lazy CollaboratorUseCase collaboratorUseCase) {
        this.collaboratorUseCase = collaboratorUseCase;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        var collaborator = this.collaboratorUseCase.findByLogin(login);

        return new User(login, collaborator.getPassword(), this.mapRolesToAuthorities(collaborator.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role ->  new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}