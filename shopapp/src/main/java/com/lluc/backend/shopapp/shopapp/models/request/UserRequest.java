package com.lluc.backend.shopapp.shopapp.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


public class UserRequest {  
    
    @NotBlank(message = "Username is mandatory")
    private String username;
    
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email is not valid")
    private String email;
    
    private String password;
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
