package com.lluc.backend.shopapp.shopapp.models.entities;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class SustainableCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "sustainableCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SustainableCategoryTranslation> translations;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public List<SustainableCategoryTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<SustainableCategoryTranslation> translations) {
        this.translations = translations;
    }
}