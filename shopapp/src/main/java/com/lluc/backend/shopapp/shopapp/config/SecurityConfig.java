package com.lluc.backend.shopapp.shopapp.config;  // 📌 Asegúrate de que esté en el paquete correcto

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  // Indica que es una configuración de Spring
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Desactiva CSRF para pruebas (en producción es mejor activarlo)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll() // Permite acceso a todos los endpoints sin autenticación
            );
        return http.build();
    }
}