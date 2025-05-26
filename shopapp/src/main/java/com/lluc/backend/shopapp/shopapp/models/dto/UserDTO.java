package com.lluc.backend.shopapp.shopapp.models.dto;

import lombok.Getter;
import lombok.Setter;

public class UserDTO {
     
    @Getter @Setter private Long id;
    @Getter @Setter private String username;
    @Getter @Setter private String email;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String token;
    @Getter @Setter private Long companyId; // ID de la empresa asociada al usuario

    public UserDTO(Long id, String username, String email, String firstName, String lastName, String token, Long companyId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.companyId = companyId;
    }

    public UserDTO() {
    }
}