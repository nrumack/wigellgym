package com.example.WigellGym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("Courtney")
                .password("{noop}Love")
                .roles("ADMIN")
                .build();

        UserDetails user1 = User.withUsername("Kurt")
                .password("{noop}Cobain")
                .roles("USER")
                .build();

        UserDetails user2 = User.withUsername("Dave")
                .password("{noop}Grohl")
                .roles("USER")
                .build();

        UserDetails user3 = User.withUsername("Krist")
                .password("{noop}Novoselic")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user1, user2, user3);
    }

}
