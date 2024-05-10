package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.auth0.jwt.JWT;
import com.automated.restaurant.automatedRestaurant.core.data.requests.AuthenticationRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.RefreshTokenAuthenticationRequest;
import com.automated.restaurant.automatedRestaurant.core.data.responses.AuthenticationResponse;
import com.automated.restaurant.automatedRestaurant.core.infra.security.JwtUtils;
import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.core.utils.TimeUtils;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Collaborator;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RefreshToken;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Role;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.CollaboratorInvalidLoginException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.NotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.RefreshTokenRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.AuthenticationUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.CollaboratorUseCase;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthenticationUseCaseImpl implements AuthenticationUseCase {

    private final CollaboratorUseCase collaboratorUseCase;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthenticationUseCaseImpl(
            CollaboratorUseCase collaboratorUseCase,
        PasswordEncoder passwordEncoder,
        JwtUtils jwtUtils,
        RefreshTokenRepository refreshTokenRepository
    ) {
        this.collaboratorUseCase = collaboratorUseCase;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        var collaborator = this.collaboratorUseCase.findByLogin(request.getLogin());

        if(!this.passwordEncoder.matches(request.getPassword(), collaborator.getPassword())) {
            throw new CollaboratorInvalidLoginException();
        }

        var token = this.generateToken(collaborator, request.getLogin());

        var refreshToken = this.refreshTokenRepository.save(RefreshToken.fromCollaborator(collaborator));

        return new AuthenticationResponse(token, refreshToken.getId());
    }

    @Override
    @Transactional
    public AuthenticationResponse authenticateWithRefreshToken(RefreshTokenAuthenticationRequest request) {

        var refreshToken = this.findRefreshToken(request.getRefreshToken());

        var token = this.generateToken(refreshToken.getCollaborator());

        refreshToken.setLastModifiedDate(LocalDateTime.now());

        this.refreshTokenRepository.delete(refreshToken);

        var newRefreshToken = this.refreshTokenRepository.save(RefreshToken.fromCollaborator(refreshToken.getCollaborator()));

        return new AuthenticationResponse(token, newRefreshToken.getId());
    }

    @Override
    public RefreshToken findRefreshToken(UUID refreshToken) {
        return this.refreshTokenRepository.findById(refreshToken).orElseThrow(
            () -> new NotFoundException(ErrorMessages.ERROR_REFRESH_TOKEN_NOT_FOUND_BY_ID.getMessage())
        );
    }

    private String generateToken(Collaborator collaborator, String login) {
        try {
            return JWT.create()
                    .withIssuer(jwtUtils.APPLICATION_ISSUER)
                    .withSubject(login)
                    .withExpiresAt(LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of(TimeUtils.BRAZIL_ZONE_OFFSET)))
                    .withClaim("id", collaborator.getId().toString())
                    .withClaim("roles", collaborator.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                    .withClaim("cpf", collaborator.getCpf())
                    .withClaim("email", collaborator.getEmail())
                    .withClaim("restaurantId", collaborator.getRestaurant().getId().toString())
                    .sign(jwtUtils.TOKEN_ALGORITHM);

        } catch (Exception exception) {
            throw new RuntimeException(ErrorMessages.ERROR_DURING_TOKEN_GENERATION.getMessage());
        }
    }

    private String generateToken(Collaborator collaborator) {
        try {
            return JWT.create()
                    .withIssuer(jwtUtils.APPLICATION_ISSUER)
                    .withExpiresAt(LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of(TimeUtils.BRAZIL_ZONE_OFFSET)))
                    .withClaim("id", collaborator.getId().toString())
                    .withClaim("roles", collaborator.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                    .withClaim("cpf", collaborator.getCpf())
                    .withClaim("email", collaborator.getEmail())
                    .withClaim("restaurantId", collaborator.getRestaurant().getId().toString())
                    .sign(jwtUtils.TOKEN_ALGORITHM);

        } catch (Exception exception) {
            throw new RuntimeException(ErrorMessages.ERROR_DURING_TOKEN_GENERATION.getMessage());
        }
    }
}
