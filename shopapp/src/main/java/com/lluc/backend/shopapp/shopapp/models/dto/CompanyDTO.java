package com.lluc.backend.shopapp.shopapp.models.dto;
import java.util.List;

import com.lluc.backend.shopapp.shopapp.models.entities.User;


public class CompanyDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String email;
    private List<ProductDTO> products;
    private User administrator;
    private int version;

    // Constructor
    public CompanyDTO(Long id, String name, String description, String address, String phoneNumber, String email, List<ProductDTO> products, User administrator, int version) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.products = products;
        this.administrator = administrator;
        this.version = version;
    }

    public Long getId() {
        return id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    

}
