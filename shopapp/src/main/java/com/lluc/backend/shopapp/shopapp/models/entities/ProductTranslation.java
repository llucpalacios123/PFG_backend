package com.lluc.backend.shopapp.shopapp.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class ProductTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String languageCode; // Código del idioma, por ejemplo, "en", "es", "fr"

    private String name; // Nombre del producto en el idioma correspondiente

    private String description; // Descripción del producto en el idioma correspondiente

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("product-translation") // Nombre único para esta relación
    private Product product;
    
    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}