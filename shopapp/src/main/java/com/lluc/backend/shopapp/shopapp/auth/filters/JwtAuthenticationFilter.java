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
import static com.lluc.backend.shopapp.shopapp.auth.TokenJwtConfig.*;
import com.lluc.backend.shopapp.shopapp.models.User;

import io.jsonwebtoken.Claims;
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
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        
        User user = new User();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        user.setUsername(username);
        user.setPassword(password);

        logger.info("Username: " + username + ", Password: " + password);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
        Authentication authResult) throws IOException, ServletException {

        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();
        
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        
        boolean isAdmin = authorities.stream()
            .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        // Serializar authorities como una lista de cadenas
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(AUTHORITIES_KEY, authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList())); // Lista de roles como cadenas
        claimsMap.put("isAdmin", isAdmin);
        
        // Crear el token JWT
        String token = Jwts.builder()
            .setClaims(claimsMap) // Agregar los claims
            .setSubject(username) // Usuario
            .setIssuer("your-issuer") // Emisor
            .setAudience("your-audience") // Audiencia
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Tiempo de expiración
            .setIssuedAt(new Date()) // Fecha de emisión
            .signWith(SECRET_KEY) // Clave secreta
            .compact();
        
        // Agregar el token al encabezado de la respuesta
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);
        
        // Respuesta en el cuerpo
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", "Authentication successful");
        
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