package com.lluc.backend.shopapp.shopapp.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class SustainableCategoryTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String languageCode;
    private String name;

    @ManyToOne
    @JoinColumn(name = "sustainable_category_id", nullable = false)
    @JsonBackReference 
    private SustainableCategory sustainableCategory;

    // Getters and Setters
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

    public SustainableCategory getSustainableCategory() {
        return sustainableCategory;
    }

    public void setSustainableCategory(SustainableCategory sustainableCategory) {
        this.sustainableCategory = sustainableCategory;
    }
}