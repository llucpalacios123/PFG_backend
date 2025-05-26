package com.lluc.backend.shopapp.shopapp.models.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class ProductDTO {

    @Getter @Setter private Long id;
    @Getter @Setter private List<ProductTranslationDTO> translations; // Nombre y descripción en diferentes idiomas
    @Getter @Setter private Long categoryId; // ID de la categoría principal del producto
    @Getter @Setter private List<Long> sustainableCategoryIds; // IDs de las categorías sostenibles
    @Getter @Setter private BigDecimal tax; // Impuestos aplicados
    @Getter @Setter private List<String> photos; // Lista de URLs de fotos del producto
    @Getter @Setter private Long companyId; // ID de la empresa asociada
    @Getter @Setter private List<PricingDTO> pricing; // Lista de precios personalizados
    @Getter @Setter private LocalDateTime fechaAlta; 
    @Getter @Setter private List<ReviewDTO> reviews; // Lista de reviews asociadas al producto
    @Getter @Setter private List<ReportDTO> reports; // Lista de reports asociadas al producto
    @Getter @Setter private boolean active; // Indica si el producto está activo o no
}