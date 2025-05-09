
package com.lluc.backend.shopapp.shopapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.lluc.backend.shopapp.shopapp.auth.filters.JwtAuthenticationFilter;
import com.lluc.backend.shopapp.shopapp.auth.filters.JwtValidationFilter;

import static com.lluc.backend.shopapp.shopapp.auth.TokenJwtConfig.*;

import java.util.Arrays;

@Configuration

public class SpringSecurityConfig{

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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
                .requestMatchers(HttpMethod.GET,"/i18n/**").permitAll() 
                .requestMatchers(HttpMethod.POST,"/users").permitAll()
                .requestMatchers(HttpMethod.POST,"/categories").permitAll()
                .requestMatchers(HttpMethod.GET,"/users").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/users/changepswd").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT,"/users/update").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST,"/company").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/products").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .csrf(config -> config.disable())// Desactivar CSRF para simplificar (no recomendado en producción)
            .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// Usar sesiones sin estado
            .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtTokenProvider)) // Añadir el filtro de autenticación JWT
            .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager(), jwtTokenProvider)) // Añadir el filtro de validación JWT
            .cors(cors-> cors.configurationSource(corsConfigurationSource())); // Configurar CORS
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(ROUTE_PRINCIPAL));
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(HEADER_AUTHORIZATION, "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE); 
        return bean;
    }
  
}

