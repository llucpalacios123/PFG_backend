package com.lluc.backend.shopapp.shopapp.models.dto.mapper;

import com.lluc.backend.shopapp.shopapp.models.dto.UpdateUserDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.User;

public class DTOMapperUpdateUser {

    private DTOMapperUpdateUser() {
    }

    public static DTOMapperUpdateUser getInstance() {
        return new DTOMapperUpdateUser();
    }

    // Convert UpdateUserDTO to User
    public static User toEntity(UpdateUserDTO updateUserDTO) {
        if (updateUserDTO == null) {
            throw new RuntimeException("UpdateUserDTO is null");
        }
        User user = new User();
        user.setId(updateUserDTO.getId());
        user.setUsername(updateUserDTO.getUsername());
        user.setEmail(updateUserDTO.getEmail());
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        return user;
    }

    // Convert User to UpdateUserDTO
    public static UpdateUserDTO toDTO(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        return new UpdateUserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName()
        );
    }
}