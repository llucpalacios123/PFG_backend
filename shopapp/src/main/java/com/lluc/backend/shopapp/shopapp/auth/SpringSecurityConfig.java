
package com.lluc.backend.shopapp.shopapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.lluc.backend.shopapp.shopapp.auth.filters.JwtAuthenticationFilter;
import com.lluc.backend.shopapp.shopapp.auth.filters.JwtValidationFilter;

@Configuration

public class SpringSecurityConfig{

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers(HttpMethod.GET,"/users").permitAll() // Permitir acceso público a /users
                .requestMatchers(HttpMethod.POST,"/users").permitAll() 
                .anyRequest().authenticated() // Requerir autenticación para cualquier otra solicitud
            )
            .csrf(config -> config.disable())// Desactivar CSRF para simplificar (no recomendado en producción)
            .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// Usar sesiones sin estado
            .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager())) // Añadir el filtro de autenticación JWT
            .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()));
        return http.build();
    }
  
}

