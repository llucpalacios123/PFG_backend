
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
import com.lluc.backend.shopapp.shopapp.repositories.UsersRepository;

import static com.lluc.backend.shopapp.shopapp.auth.TokenJwtConfig.*;

import java.util.Arrays;

@Configuration

public class SpringSecurityConfig{

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsersRepository usersRepository;

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
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.GET,"/users").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/users/changepswd").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT,"/users/update").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/orders").hasAnyRole("USER", "ADMIN") // Obtener historial de pedidos
                .requestMatchers(HttpMethod.POST,"/company").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/company/orders/grouped").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/products").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/products/company").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/products/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/products/search").permitAll()
                .requestMatchers(HttpMethod.POST,"/categories").permitAll()
                .requestMatchers(HttpMethod.GET,"/categories").permitAll()
                .requestMatchers(HttpMethod.GET,"/categories/sustainable").permitAll()
                .requestMatchers(HttpMethod.POST, "images/upload").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "images/delete/{key}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/resendEmail").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/verify").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/addresses").hasAnyRole("USER", "ADMIN") // Obtener todas las direcciones del usuario autenticado
                .requestMatchers(HttpMethod.POST, "/users/addresses").hasAnyRole("USER", "ADMIN") // Añadir dirección
                .requestMatchers(HttpMethod.DELETE, "/users/addresses/{addressId}").hasAnyRole("USER", "ADMIN") // Eliminar dirección
                .requestMatchers(HttpMethod.PUT, "/users/addresses/{addressId}").hasAnyRole("USER", "ADMIN") // Modificar dirección
                .requestMatchers(HttpMethod.POST, "/orders").hasAnyRole("USER", "ADMIN") // Crear un pedido con productos
                .requestMatchers(HttpMethod.PUT, "/orders/{id}/status").hasAnyRole("USER", "ADMIN") // Actualizar el estado de un producto en un pedido
                .anyRequest().authenticated()
            )
            .csrf(config -> config.disable())// Desactivar CSRF para simplificar (no recomendado en producción)
            .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// Usar sesiones sin estado
            .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtTokenProvider, usersRepository)) // Añadir el filtro de autenticación JWT
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

