package com.lluc.backend.shopapp.shopapp.models.dto.mapper;

import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.User;

public class DTOMapperUser {
    
    private DTOMapperUser() {
    }

    public static DTOMapperUser getInstance() {
        return new DTOMapperUser();
    }

    // Convertir de User a UserDTO
    public static UserDTO toDTO(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        return new UserDTO(
            user.getId(), 
            user.getUsername(), 
            user.getEmail(), 
            user.getFirstName(), 
            user.getLastName(), 
            null // Puedes mapear roles o cualquier otra propiedad adicional si es necesario
        );
    }

    // Convertir de UserDTO a User
    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            throw new RuntimeException("UserDTO is null");
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        // Puedes mapear roles o cualquier otra propiedad adicional si es necesario
        return user;
    }
}