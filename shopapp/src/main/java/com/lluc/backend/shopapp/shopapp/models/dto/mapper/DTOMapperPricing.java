package com.lluc.backend.shopapp.shopapp.models.dto.mapper;

import com.lluc.backend.shopapp.shopapp.models.dto.PricingDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.PricingValueDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.Pricing;
import com.lluc.backend.shopapp.shopapp.models.entities.PricingValue;

import java.util.stream.Collectors;

public class DTOMapperPricing {

    // Convertir de Pricing a PricingDTO
    public static PricingDTO toPricingDTO(Pricing pricing) {
        if (pricing == null) {
            return null;
        }

        PricingDTO pricingDTO = new PricingDTO();
        pricingDTO.setCategoryName(pricing.getCategoryName());
        pricingDTO.setValues(
            pricing.getValues().stream()
                .map(DTOMapperPricing::toPricingValueDTO)
                .collect(Collectors.toList())
        );

        return pricingDTO;
    }

    // Convertir de PricingDTO a Pricing
    public static Pricing toPricingEntity(PricingDTO pricingDTO) {
        if (pricingDTO == null) {
            return null;
        }

        Pricing pricing = new Pricing();
        pricing.setCategoryName(pricingDTO.getCategoryName());
        pricing.setValues(
            pricingDTO.getValues().stream()
                .map(DTOMapperPricing::toPricingValueEntity)
                .collect(Collectors.toList())
        );

        return pricing;
    }

    public static PricingValueDTO toPricingValueDTO(PricingValue value) {
        if (value == null) {
            return null;
        }

        PricingValueDTO valueDTO = new PricingValueDTO();
        valueDTO.setCategoryValue(value.getCategoryValue());
        valueDTO.setPrice(value.getPrice());
        valueDTO.setCost(value.getCost());
        valueDTO.setStock(value.getStock());

        return valueDTO;
    }

    // Convertir de PricingValueDTO a PricingValue
    public static PricingValue toPricingValueEntity(PricingValueDTO valueDTO) {
        if (valueDTO == null) {
            return null;
        }

        PricingValue value = new PricingValue();
        value.setCategoryValue(valueDTO.getCategoryValue());
        value.setPrice(valueDTO.getPrice());
        value.setCost(valueDTO.getCost());
        value.setStock(valueDTO.getStock());

        return value;
    }
}