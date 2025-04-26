package com.lluc.backend.shopapp.shopapp.models.dto;

import lombok.Getter;
import lombok.Setter;

public class UserDTO {
     
    @Getter @Setter private Long id;
    @Getter @Setter private String username;
    @Getter @Setter private String email;
    
    
    public UserDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }


    public UserDTO() {
    }
    
    

    

}
