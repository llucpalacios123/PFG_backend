package com.lluc.backend.shopapp.shopapp.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference("product-report") // Marca esta relación como la parte "inversa"
    @Getter @Setter private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-report") // Marca esta relación como la parte "inversa"
    @Getter @Setter private User user;

    @Getter @Setter private String comment; // Detalles adicionales del reporte
}