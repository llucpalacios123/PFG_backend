package com.lluc.backend.shopapp.shopapp.models.dto.mapper;

import com.lluc.backend.shopapp.shopapp.models.dto.CompanyDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.Company;
import com.lluc.backend.shopapp.shopapp.models.request.CompanyRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    // Mapear de Company a CompanyDTO
    @Mapping(target = "products", ignore = true) // Ignorar productos si no son necesarios
    CompanyDTO toDTO(Company company);

    // Mapear de CompanyDTO a Company
    @Mapping(target = "products", ignore = true) // Ignorar productos si no son necesarios
    Company toEntity(CompanyDTO companyDTO);

    Company toEntity(CompanyRequest companyRequest);
}