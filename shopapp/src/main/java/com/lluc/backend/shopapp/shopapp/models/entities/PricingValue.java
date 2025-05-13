package com.lluc.backend.shopapp.shopapp.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "pricing_value") // Asegúrate de que el nombre de la tabla sea consistente
public class PricingValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false) // Asegura que este campo no sea nulo
    private String categoryValue; // Valor específico (por ejemplo, "XL")

    @Getter
    @Setter
    @Column(nullable = false, precision = 10, scale = 2) // Define precisión para valores monetarios
    private BigDecimal price; // Precio

    @Getter
    @Setter
    @Column(nullable = false, precision = 10, scale = 2) // Define precisión para valores monetarios
    private BigDecimal cost; // Costo

    @Getter
    @Setter
    @Column(nullable = false) // Asegura que este campo no sea nulo
    private Integer stock; // Stock

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY) // Usa carga diferida para optimizar el rendimiento
    @JoinColumn(name = "pricing_id", nullable = false)
    @JsonBackReference("pricing-value")
    private Pricing pricing; // Relación con la entidad Pricing
}