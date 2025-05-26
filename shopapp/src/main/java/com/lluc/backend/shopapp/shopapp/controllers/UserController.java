package com.lluc.backend.shopapp.shopapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.lluc.backend.shopapp.shopapp.models.dto.OrderDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.UpdateUserDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.mapper.UpdateUserMapper;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.models.entities.UserAddress;
import com.lluc.backend.shopapp.shopapp.models.request.UserAddressRequest;
import com.lluc.backend.shopapp.shopapp.services.interfaces.UserService;

import jakarta.validation.ConstraintViolationException;
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
        if (result.hasErrors()) {
            return validationErrors(result);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        } catch (DataIntegrityViolationException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Violación de restricción de la base de datos");
            error.put("details", ex.getMostSpecificCause().getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (ConstraintViolationException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Violación de restricción de la base de datos");
            error.put("details", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserDTO user, BindingResult result) {
        if (result.hasErrors()) {
            return validationErrors(result);
        }
        // Usar MapStruct para convertir de UpdateUserDTO a User
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(UpdateUserMapper.INSTANCE.toEntity(user)));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser() {
        // Obtén el nombre de usuario del usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Busca al usuario en la base de datos por su nombre de usuario
        Optional<UserDTO> user = userService.findByUsernameDTO(username);

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


    // Añadir una dirección de entrega
@PostMapping("/addresses")
public ResponseEntity<?> addAddress(@RequestBody UserAddressRequest addressRequest) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    try {
        UserAddress address = new UserAddress();
        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setPostalCode(addressRequest.getPostalCode());
        address.setCountry(addressRequest.getCountry());

        UserAddress savedAddress = userService.addAddress(username, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

// Eliminar una dirección de entrega
@DeleteMapping("/addresses/{addressId}")
public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    try {
        userService.deleteAddress(username, addressId);
        return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

// Modificar una dirección de entrega
@PutMapping("/addresses/{addressId}")
public ResponseEntity<?> updateAddress(@PathVariable Long addressId, @RequestBody UserAddressRequest addressRequest) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    try {
        UserAddress updatedAddress = new UserAddress();
        updatedAddress.setStreet(addressRequest.getStreet());
        updatedAddress.setCity(addressRequest.getCity());
        updatedAddress.setState(addressRequest.getState());
        updatedAddress.setPostalCode(addressRequest.getPostalCode());
        updatedAddress.setCountry(addressRequest.getCountry());

        UserAddress savedAddress = userService.updateAddress(username, addressId, updatedAddress);
        return ResponseEntity.ok(savedAddress);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

    @GetMapping("/addresses")
    public ResponseEntity<?> getAddresses() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            List<UserAddress> addresses = userService.getAddresses(username);
            return ResponseEntity.ok(addresses);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrderHistory() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            List<OrderDTO> orderHistory = userService.getOrderHistory(username);
            return ResponseEntity.ok(orderHistory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}