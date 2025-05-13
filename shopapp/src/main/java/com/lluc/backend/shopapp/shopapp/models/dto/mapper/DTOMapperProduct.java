package com.lluc.backend.shopapp.shopapp.models.dto.mapper;

import com.lluc.backend.shopapp.shopapp.models.dto.ProductDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.ProductTranslationDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.PricingDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.Product;
import com.lluc.backend.shopapp.shopapp.models.entities.ProductTranslation;

import java.util.List;
import java.util.stream.Collectors;

public class DTOMapperProduct {

    // Convertir de Product a ProductDTO
    public static ProductDTO toProductDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setTranslations(
            product.getTranslations().stream()
                .map(DTOMapperProduct::toProductTranslationDTO)
                .collect(Collectors.toList())
        );
        productDTO.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        productDTO.setSustainableCategoryIds(
            product.getSustainableCategories().stream()
                .map(sustainableCategory -> sustainableCategory.getId())
                .collect(Collectors.toList())
        );
        productDTO.setTax(product.getTax());
        productDTO.setPhotos(product.getPhotos());
        productDTO.setPricing(
            product.getPricing().stream()
                .map(DTOMapperPricing::toPricingDTO)
                .collect(Collectors.toList())
        );
        productDTO.setCompanyId(product.getCompany() != null ? product.getCompany().getId() : null);
        productDTO.setFechaAlta(product.getFechaAlta());

        return productDTO;
    }

    // Convertir de ProductDTO a Product
    public static Product toProductEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productDTO.getId());
        // Las traducciones deben ser manejadas por separado, ya que requieren la relación con el producto
        product.setTax(productDTO.getTax());
        product.setPhotos(productDTO.getPhotos());
        product.setPricing(
            productDTO.getPricing().stream()
                .map(DTOMapperPricing::toPricingEntity)
                .collect(Collectors.toList())
        );

        return product;
    }

    // Convertir de ProductTranslation a ProductTranslationDTO
    public static ProductTranslationDTO toProductTranslationDTO(ProductTranslation translation) {
        if (translation == null) {
            return null;
        }

        ProductTranslationDTO translationDTO = new ProductTranslationDTO();
        translationDTO.setId(translation.getId());
        translationDTO.setLanguageCode(translation.getLanguageCode());
        translationDTO.setName(translation.getName());
        translationDTO.setDescription(translation.getDescription());

        return translationDTO;
    }

    // Convertir de ProductTranslationDTO a ProductTranslation
    public static ProductTranslation toProductTranslationEntity(ProductTranslationDTO translationDTO) {
        if (translationDTO == null) {
            return null;
        }

        ProductTranslation translation = new ProductTranslation();
        translation.setId(translationDTO.getId());
        translation.setLanguageCode(translationDTO.getLanguageCode());
        translation.setName(translationDTO.getName());
        translation.setDescription(translationDTO.getDescription());

        return translation;
    }

    public static List<ProductDTO> toProductDTOList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return List.of(); 
        }

        return products.stream()
                .map(DTOMapperProduct::toProductDTO)
                .collect(Collectors.toList());
    }
    public static List<Product> toProductEntityList(List<ProductDTO> productDTOs) {
        if (productDTOs == null || productDTOs.isEmpty()) {
            return List.of(); 
        }

        return productDTOs.stream()
                .map(DTOMapperProduct::toProductEntity)
                .collect(Collectors.toList());
    }
}