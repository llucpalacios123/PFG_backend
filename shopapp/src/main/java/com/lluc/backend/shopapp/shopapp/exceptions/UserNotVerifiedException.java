package com.lluc.backend.shopapp.shopapp.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotVerifiedException extends AuthenticationException {

    private final String email;

    public UserNotVerifiedException(String message, String email) {
        super(message);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}