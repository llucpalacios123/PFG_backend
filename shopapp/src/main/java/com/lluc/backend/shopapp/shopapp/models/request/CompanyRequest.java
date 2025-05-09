package com.lluc.backend.shopapp.shopapp.models.request;

import jakarta.validation.constraints.NotNull;

public class CompanyRequest {
    
    @NotNull(message = "Name is mandatory")
    private String name;
    private String description;

    public CompanyRequest() {
    }

    public CompanyRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
