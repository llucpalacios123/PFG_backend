package com.lluc.backend.shopapp.shopapp.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Pricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String categoryName; // Nombre de la categoría (por ejemplo, "Talla")

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference("product-pricing") // Nombre único para esta relación
    private Product product;

    @Getter
    @Setter
    @OneToMany(mappedBy = "pricing", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("pricing-value") 
    private List<PricingValue> values; // Lista de valores específicos
}