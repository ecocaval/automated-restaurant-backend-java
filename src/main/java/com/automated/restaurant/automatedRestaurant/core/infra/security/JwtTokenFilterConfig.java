package com.automated.restaurant.automatedRestaurant.core.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("jwtUtils")
public class JwtTokenFilterConfig {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtTokenFilterConfig(JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtVerifier(), customUserDetailsService);
    }

    @Bean
    public JWTVerifier jwtVerifier() {

        return JWT.require(jwtUtils.TOKEN_ALGORITHM)
                .withIssuer(jwtUtils.APPLICATION_ISSUER)
                .build();
    }
}
