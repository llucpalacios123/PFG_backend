package com.lluc.backend.shopapp.shopapp.models.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {

    private Long id;
    private List<ProductTranslationDTO> translations; // Nombre y descripción en diferentes idiomas
    private Long categoryId; // ID de la categoría principal del producto
    private List<Long> sustainableCategoryIds; // IDs de las categorías sostenibles
    private BigDecimal tax; // Impuestos aplicados
    private List<String> photos; // Lista de URLs de fotos del producto
    private Long companyId; // ID de la empresa asociada
    private List<PricingDTO> pricing; // Lista de precios personalizados

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductTranslationDTO> getTranslations() {
        return translations;
    }

    public void setTranslations(List<ProductTranslationDTO> translations) {
        this.translations = translations;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Long> getSustainableCategoryIds() {
        return sustainableCategoryIds;
    }

    public void setSustainableCategoryIds(List<Long> sustainableCategoryIds) {
        this.sustainableCategoryIds = sustainableCategoryIds;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<PricingDTO> getPricing() {
        return pricing;
    }

    public void setPricing(List<PricingDTO> pricing) {
        this.pricing = pricing;
    }
}