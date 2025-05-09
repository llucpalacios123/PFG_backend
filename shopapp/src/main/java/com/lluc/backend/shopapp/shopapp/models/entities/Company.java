package com.lluc.backend.shopapp.shopapp.models.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "Empresa")
public class Company {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private String phoneNumber;
    
    @Getter
    @Setter
    @Email
    @NotNull
    private String email;

    @Getter
    @Setter
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @Getter
    @Setter
    @OneToOne
    private User administrator;

    @Version
    @Getter
    @Setter
    private Integer version = 0; 
}