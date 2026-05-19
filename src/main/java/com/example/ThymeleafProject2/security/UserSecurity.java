package com.example.ThymeleafProject2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.ThymeleafProject2.repository.UserRepository;

@Configuration
public class UserSecurity {

    // BCrypt Password Encoder
    @Bean
    BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // Fetch User From Database
    @Bean
    UserDetailsService userDetailsService(
            UserRepository userRepository) {

        return username -> {

            com.example.ThymeleafProject2.entity.User user =
                    userRepository
                    .findByUsername(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(
                                    "User Not Found"));

            UserDetails details =
                    User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();

            return details;
        };
    }

    // Security Rules
    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                    // Public URLs
                    .requestMatchers(
                            "/register",
                            "/save",
                            "/login"
                    ).permitAll()

                    // Admin Access
                    .requestMatchers("/admin")
                    .hasRole("ADMIN")

                    // User + Admin Access
                    .requestMatchers(
                            "/dashboard",
                            "/profile"
                    ).hasAnyRole(
                            "USER",
                            "ADMIN"
                    )

                    // Remaining URLs
                    .anyRequest()
                    .authenticated()
            )

            // Login Configuration
            .formLogin(form -> form

                    .loginPage("/login")

                    .defaultSuccessUrl(
                            "/dashboard",
                            true
                    )

                    .permitAll()
            )

            // Logout
            .logout(logout -> logout

                    .logoutSuccessUrl("/login")
                    .permitAll()
            );

        return http.build();
    }
}