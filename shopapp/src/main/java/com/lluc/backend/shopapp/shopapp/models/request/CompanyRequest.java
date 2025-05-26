package com.lluc.backend.shopapp.shopapp.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class CompanyRequest {

    @NotNull(message = "null_name")
    private String name;

    private String description;

    @NotNull(message = "Fiscal name is mandatory")
    private String fiscalName; // Nombre fiscal de la compañía

    @NotNull(message = "Fiscal ID is mandatory")
    private String fiscalId; // Número de identificación fiscal (NIF)

    private String fiscalAddress; // Dirección fiscal

    @NotNull(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    public CompanyRequest() {
    }

    public CompanyRequest(String name, String description, String fiscalName, String fiscalId, String fiscalAddress, String email) {
        this.name = name;
        this.description = description;
        this.fiscalName = fiscalName;
        this.fiscalId = fiscalId;
        this.fiscalAddress = fiscalAddress;
        this.email = email;
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

    public String getFiscalName() {
        return fiscalName;
    }

    public void setFiscalName(String fiscalName) {
        this.fiscalName = fiscalName;
    }

    public String getFiscalId() {
        return fiscalId;
    }

    public void setFiscalId(String fiscalId) {
        this.fiscalId = fiscalId;
    }

    public String getFiscalAddress() {
        return fiscalAddress;
    }

    public void setFiscalAddress(String fiscalAddress) {
        this.fiscalAddress = fiscalAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}