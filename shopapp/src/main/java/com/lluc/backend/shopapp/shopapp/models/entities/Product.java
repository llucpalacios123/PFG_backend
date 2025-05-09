package com.lluc.backend.shopapp.shopapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductTranslation> translations; // Nombre y descripción en diferentes idiomas

    @Getter
    @Setter
    @ManyToOne
    private Category category; // Categoría principal del producto

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
        name = "product_sustainable_categories",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "sustainable_category_id")
    )
    private List<SustainableCategory> sustainableCategories; // Lista de categorías sostenibles

    @Getter
    @Setter
    private BigDecimal tax; // Impuestos aplicados

    @Getter
    @Setter
    @ElementCollection
    private List<String> photos; // Lista de URLs de fotos del producto

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company; // Relación con la entidad Company

    @Getter
    @Setter
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pricing> pricing; // Lista de precios personalizados
}