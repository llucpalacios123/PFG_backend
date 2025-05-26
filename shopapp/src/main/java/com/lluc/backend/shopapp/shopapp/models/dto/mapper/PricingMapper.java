package com.lluc.backend.shopapp.shopapp.models.dto.mapper;

import com.lluc.backend.shopapp.shopapp.models.dto.PricingDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.PricingValueDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.Pricing;
import com.lluc.backend.shopapp.shopapp.models.entities.PricingValue;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PricingMapper {

    PricingMapper INSTANCE = Mappers.getMapper(PricingMapper.class);

    // Mapear de Pricing a PricingDTO
    PricingDTO toPricingDTO(Pricing pricing);

    // Mapear de PricingDTO a Pricing
    Pricing toPricingEntity(PricingDTO pricingDTO);

    // Mapear de PricingValue a PricingValueDTO
    PricingValueDTO toPricingValueDTO(PricingValue value);

    // Mapear de PricingValueDTO a PricingValue
    PricingValue toPricingValueEntity(PricingValueDTO valueDTO);
}