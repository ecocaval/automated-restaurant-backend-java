package com.automated.restaurant.automatedRestaurant.core.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Autowired
    public SecurityConfig(@Lazy JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                            .requestMatchers(HttpMethod.GET, "v1/restaurant/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "v1/restaurant").permitAll()
                            .requestMatchers(HttpMethod.GET, "v1/customer/restaurant/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "v1/customer/restaurant/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "v1/bill/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "v1/bill/**").permitAll()
                            .requestMatchers(HttpMethod.PATCH, "v1/bill/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "v1/collaborator/restaurant/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "v1/collaborator/auth/**").permitAll()
                            .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

