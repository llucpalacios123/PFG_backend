package com.lluc.backend.shopapp.shopapp.models.entities;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CategoryTranslation> translations;
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("category-product") // Nombre único para esta relación
    private List<Product> products;

    private String image;

    // Getters and Setters
    public Long getId() {
        return id;
    }   

    public void setId(Long id) {
        this.id = id;
    }

    public List<CategoryTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<CategoryTranslation> translations) {
        this.translations = translations;
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}