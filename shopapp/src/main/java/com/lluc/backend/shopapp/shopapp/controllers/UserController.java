package com.lluc.backend.shopapp.shopapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lluc.backend.shopapp.shopapp.models.dto.UpdateUserDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.mapper.DTOMapperUpdateUser;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.services.interfaces.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*")
public class UserController {
    
    @Autowired
    private UserService userService;   

    @GetMapping
    public List<UserDTO> listUsers() {
        return userService.findAll();
    }

    private ResponseEntity<?> validationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        
        return ResponseEntity.badRequest().body(errors); 
    }

    
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()) {
            return validationErrors(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserDTO user, BindingResult result) {
        if(result.hasErrors()) {
            return validationErrors(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(DTOMapperUpdateUser.toEntity(user)));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser() {
        // Obtén el nombre de usuario del usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Busca al usuario en la base de datos por su nombre de usuario
        Optional<UserDTO> user = userService.findByUsername(username);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }
                
                
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        
        Optional<UserDTO> user = userService.findById(id);
        
        if (user.isPresent()) {
            return ResponseEntity.ok(user.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @PostMapping("/changepswd")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> passwords) {
        String oldPassword = passwords.get("oldPassword");
        String newPassword = passwords.get("newPassword");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            userService.changePassword(username, oldPassword, newPassword);
            return ResponseEntity.ok("succes.changePassword");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/resendEmail")
    public ResponseEntity<?> resendEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body("error.email.required"); // Clave para i18n
        }

        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getVerified()) {
                return ResponseEntity.badRequest().body("error.user.already.verified"); // Clave para i18n
            }

            // Lógica para reenviar el correo de verificación
            userService.resendVerificationEmail(user);

            return ResponseEntity.ok("success.email.resent"); // Clave para i18n
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error.user.not.found"); // Clave para i18n
    }

}