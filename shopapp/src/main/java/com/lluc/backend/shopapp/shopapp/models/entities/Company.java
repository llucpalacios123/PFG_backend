package com.lluc.backend.shopapp.shopapp.models.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name = "company")
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
    @Email
    @NotNull
    private String email;

    // Nuevos campos para datos fiscales
    @Getter
    @Setter
    @NotNull
    private String fiscalName; // Nombre fiscal de la compañía

    @Getter
    @Setter
    @NotNull
    private String fiscalId; // Número de identificación fiscal (NIF)

    @Getter
    @Setter
    private String fiscalAddress; // Dirección fiscal

    @Getter
    @Setter
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Marca esta relación como la parte "gestora"
    private List<Product> products;

    @Getter
    @Setter
    @OneToOne
    private User administrator;

    @Version
    @Getter
    @Setter
    private Integer version = 0; 

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter private List<OrderProduct> orderProducts; // Productos en pedidos asociados a la compañía
}