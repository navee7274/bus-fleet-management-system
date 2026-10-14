package com.busfleetmanagement.system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthFilter authFilter;

    public SecurityConfig(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login",
                                "/api/auth/logout").permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/availability/buses",
                                "/api/availability/drivers",
                                "/api/bookings"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/bookings/*",
                                "/api/payments/*",
                                "/api/payments/*/paymentmethod"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/payments/notify"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/payments/*/mark"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        authFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}