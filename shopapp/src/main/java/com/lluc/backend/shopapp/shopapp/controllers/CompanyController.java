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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lluc.backend.shopapp.shopapp.models.dto.CompanyOrderDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.request.CompanyRequest;
import com.lluc.backend.shopapp.shopapp.services.interfaces.CompanyService;
import com.lluc.backend.shopapp.shopapp.services.interfaces.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
@CrossOrigin(originPatterns = "*")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    private ResponseEntity<?> validationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @PostMapping
    public ResponseEntity<?> createCompany(@Valid @RequestBody CompanyRequest company, BindingResult result) {
        if (result.hasErrors()) {
            return validationErrors(result);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Busca al usuario en la base de datos por su nombre de usuario
        Optional<UserDTO> user = userService.findByUsername(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.save(user.get(), company));
    }

    // Nuevo endpoint para obtener los pedidos agrupados por compañía
    @GetMapping("/orders/grouped")
    public ResponseEntity<?> getCompanyOrdersGroupedByOrder() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            List<CompanyOrderDTO> orders = companyService.getCompanyOrdersGroupedByOrder(username);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}