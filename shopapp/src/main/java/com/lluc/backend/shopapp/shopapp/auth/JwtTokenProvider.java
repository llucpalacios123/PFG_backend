package com.lluc.backend.shopapp.shopapp.auth;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.lluc.backend.shopapp.shopapp.auth.TokenJwtConfig.*;

@Component
public class JwtTokenProvider {

    public String generateToken(Long userId, String username, List<? extends GrantedAuthority> authorities, boolean hasCompany, boolean verification, int expirationHours) {
        // Crear el mapa de claims
        Map<String, Object> claims = Map.of(
            "userId", userId,
            "username", username,
            "roles", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()),
            "isAdmin", authorities.stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN")),
            "hasCompany", hasCompany, // Agregar si el usuario tiene empresa asociada
            "verification", verification // Agregar si el token es para verificación
        );
    
        // Calcular la fecha de expiración en milisegundos
        Date expirationDate = new Date(System.currentTimeMillis() + expirationHours * 60 * 60 * 1000);
    
        // Crear el token JWT
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setExpiration(expirationDate)
            .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
            .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}