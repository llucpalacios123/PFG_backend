package com.lluc.backend.shopapp.shopapp.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonConstructor {

    @JsonCreator
    public SimpleGrantedAuthorityJsonConstructor(
            @JsonProperty("authority") String role) {
        
    }
    
}
