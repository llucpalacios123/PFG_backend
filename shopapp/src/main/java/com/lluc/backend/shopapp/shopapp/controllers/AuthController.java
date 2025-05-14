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
                    user.setVerified(true); // Marcamos el usuario como verificado
                    usersRepository.save(user);

                    // Enviar correo de confirmación
                    String subject = "Cuenta verificada correctamente";
                    String body = "Hola " + user.getFirstName() + ",\n\nTu cuenta ha sido verificada correctamente. ¡Gracias por registrarte en Wallfri!";
                    emailService.sendRegistrationEmail(user.getEmail(), subject, body);

                    return ResponseEntity.ok("Cuenta verificada correctamente."); // 200 OK
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado."); // 404 Not Found
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token no válido para verificación."); // 400 Bad Request
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado."); // 401 Unauthorized
        }
    }
}
