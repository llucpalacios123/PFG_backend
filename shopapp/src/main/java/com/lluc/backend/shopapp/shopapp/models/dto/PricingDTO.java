package com.lluc.backend.shopapp.shopapp.models.dto;

import java.util.List;

public class PricingDTO {

    private String categoryName; // Nombre de la categoría (por ejemplo, "Talla")
    private List<PricingValueDTO> values; // Lista de valores específicos

    // Getters y Setters
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<PricingValueDTO> getValues() {
        return values;
    }

    public void setValues(List<PricingValueDTO> values) {
        this.values = values;
    }
}