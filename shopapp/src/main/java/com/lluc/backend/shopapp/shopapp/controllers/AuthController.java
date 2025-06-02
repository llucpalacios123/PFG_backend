package com.lluc.backend.shopapp.shopapp.controllers;

import com.lluc.backend.shopapp.shopapp.auth.JwtTokenProvider;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.repositories.UsersRepository;
import com.lluc.backend.shopapp.shopapp.services.Implementations.EmailService;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        try {
            Claims claims = jwtTokenProvider.validateToken(token);

            if (claims.get("verification", Boolean.class)) {
                Long userId = claims.get("userId", Long.class);
                Optional<User> userOptional = usersRepository.findById(userId);

                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    user.setVerified(true); // Marcar el usuario como verificado
                    usersRepository.save(user);

                    emailService.sendVerificationEmail(user.getEmail(), user.getFirstName());

                    return ResponseEntity.ok("Compte verificat correctament."); // 200 OK
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuari no trobat."); // 404 Not Found
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token no vàlid per a verificació."); // 400 Bad Request
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invàlid o caducat."); // 401 Unauthorized
        }
    }
}
