package com.lluc.backend.shopapp.shopapp.auth.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lluc.backend.shopapp.shopapp.auth.CustomUserDetails;
import com.lluc.backend.shopapp.shopapp.models.entities.User;

import static com.lluc.backend.shopapp.shopapp.auth.TokenJwtConfig.*;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            String username = user.getUsername();
            String password = user.getPassword();
    
            logger.info("Username: " + username + ", Password: " + password);
    
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse authentication request body", e);
        }
    }
    

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        // Obtener el principal y hacer un cast a CustomUserDetails
        CustomUserDetails principal = (CustomUserDetails) authResult.getPrincipal();
        Long userId = principal.getId(); // Ahora puedes obtener el ID del usuario

        // Obtener el username y los roles
        String username = principal.getUsername();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
		
		boolean isAdmin = authorities.stream()
            .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
		
        // Crear el mapa de claims
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("userId", userId); // Añadir el ID del usuario
        claimsMap.put("username", username);
		claimsMap.put("isAdmin", isAdmin);
        claimsMap.put("roles", authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));
		
		
        // Crear el token JWT
        String token = Jwts.builder()
            .setClaims(claimsMap)
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SECRET_KEY)
            .compact();

        // Agregar el token al encabezado de la respuesta
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        // Respuesta en el cuerpo
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Authentication failed");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }
}