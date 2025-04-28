package com.lluc.backend.shopapp.shopapp.models.dto.mapper;

import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.User;

public class DTOMapperUser {
    
    private DTOMapperUser() {
    }

    public static DTOMapperUser getInstance() {
        return new DTOMapperUser();
    }

    public static UserDTO toDTO(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), null);
    }
}
