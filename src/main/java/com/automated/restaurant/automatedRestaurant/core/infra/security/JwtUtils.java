package com.automated.restaurant.automatedRestaurant.core.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.automated.restaurant.automatedRestaurant.core.data.enums.AccountRole;
import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtUtils {

    private static final List<String> TOKEN_IDENTIFICATION_CLAIMS = List.of("id", "cpf", "email", "restaurantId");

    public final String APPLICATION_ISSUER = "default-issuer";

    public final Algorithm TOKEN_ALGORITHM;

    public JwtUtils(@Value("${api.security.token.secret}") String SECRET) {
        this.TOKEN_ALGORITHM = Algorithm.HMAC256(SECRET);
    }

    public static void validateAdminOrRestaurantCollaborator(String identification) {
        if(!JwtUtils.isAdminOrRestaurantCollaborator(identification)) {
            throw new UnauthorizedException(ErrorMessages.ERROR_INVALID_TOKEN.getMessage());
        }
    }

    private static boolean isAdminOrRestaurantCollaborator(String identification) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(accountIsAdmin(authentication)) {
            return true;
        }

        DecodedJWT token = JWT.decode(authentication.getCredentials().toString());

        List<String> listOfIdentificationsFromToken = buildTokenIdentificationsListFromClaims(token);

        return listOfIdentificationsFromToken.contains(identification);
    }

    private static List<String> buildTokenIdentificationsListFromClaims(DecodedJWT token) {

        var listOfIdentificationsFromToken = new ArrayList<String>();

        listOfIdentificationsFromToken.add(token.getSubject());

        for (String tokenIdentificationClaim : TOKEN_IDENTIFICATION_CLAIMS) {
            listOfIdentificationsFromToken.add(token.getClaim(tokenIdentificationClaim).toString().replaceAll("\"", ""));
        }

        return listOfIdentificationsFromToken;
    }

    private static boolean accountIsAdmin(Authentication authentication) {

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if(authority.getAuthority().equals(AccountRole.ADMIN.getRole())) {
                return true;
            }
        }
        return false;
    }
}
