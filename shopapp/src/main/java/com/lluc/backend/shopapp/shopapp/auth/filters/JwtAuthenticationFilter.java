package com.lluc.backend.shopapp.shopapp.auth.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lluc.backend.shopapp.shopapp.auth.CustomUserDetails;
import com.lluc.backend.shopapp.shopapp.auth.JwtTokenProvider;
import com.lluc.backend.shopapp.shopapp.exceptions.UserNotFoundException;
import com.lluc.backend.shopapp.shopapp.exceptions.UserNotVerifiedException;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.repositories.UsersRepository;

import static com.lluc.backend.shopapp.shopapp.auth.TokenJwtConfig.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private UsersRepository usersRepository; // Cambiar a UsersRepository
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider; // Eliminar @Autowired


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsersRepository usersRepository) {
        this.usersRepository = usersRepository; // Cambiar a UsersRepository
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider; // Inicializar en el constructor
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Leer el cuerpo de la solicitud para obtener el usuario y la contraseña
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            String username = user.getUsername();
            String password = user.getPassword();
    
            logger.info("Username: " + username + ", Password: " + password);
    
            // Verificar si el usuario existe en la base de datos
            User loadedUser = loadUserByUsername(username);
    
            // Si no existe, lanzamos una excepción controlada
            if (loadedUser == null) {
                throw new UserNotFoundException("Usuario no encontrado.");
            }
    
            // Verificar si el usuario está verificado
            if (!loadedUser.getVerified()) {
                throw new UserNotVerifiedException("Usuario no verificado.", loadedUser.getEmail());
            }
    
            // Crear el token de autenticación
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse authentication request body", e);
        }
    }
    

    // Método auxiliar para cargar el usuario desde la base de datos
    private User loadUserByUsername(String username) {
        return usersRepository.findByUsername(username).orElse(null);
    }
    

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
    
        // Obtener el principal y hacer un cast a CustomUserDetails
        CustomUserDetails principal = (CustomUserDetails) authResult.getPrincipal();
        Long userId = principal.getId(); // Ahora puedes obtener el ID del usuario
    
        // Obtener el username y los roles
        String username = principal.getUsername();
        List<? extends GrantedAuthority> authorities = principal.getAuthorities()
            .stream()
            .collect(Collectors.toList()); // Convert Collection to List
        
    
        String token = jwtTokenProvider.generateToken(userId, username, authorities, principal.isHasCompany(), false, 2);
    
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
    
        if (failed instanceof UserNotVerifiedException) {
            UserNotVerifiedException exception = (UserNotVerifiedException) failed;
            body.put("key", "error.usernotverified");
            body.put("email", exception.getEmail()); // Incluir el correo electrónico en la respuesta
            response.setStatus(403); // Forbidden
        } else if (failed instanceof UserNotFoundException) {
            body.put("key", "error.usernotfound");
            response.setStatus(404); // Not Found
        } else {
            body.put("key", "error.authenticationfailed");
            response.setStatus(401); // Unauthorized
        }
    
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    }

}