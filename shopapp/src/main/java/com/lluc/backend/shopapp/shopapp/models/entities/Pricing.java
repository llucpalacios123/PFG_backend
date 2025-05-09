package com.lluc.backend.shopapp.shopapp.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @JoinColumn(name = "product_id", nullable = false) // Relación con Product
    private Product product;

    @Getter
    @Setter
    @OneToMany(mappedBy = "pricing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PricingValue> values; // Lista de valores específicos
}