package com.lluc.backend.shopapp.shopapp.models.dto;

import java.math.BigDecimal;

public class PricingValueDTO {

    private String categoryValue; // Valor específico (por ejemplo, "XL")
    private BigDecimal price; // Precio
    private BigDecimal cost; // Costo
    private Integer stock; // Stock

    // Getters y Setters
    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}