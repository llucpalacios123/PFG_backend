package com.lluc.backend.shopapp.shopapp.models.dto.mapper;

import com.lluc.backend.shopapp.shopapp.models.dto.CompanyDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.ProductDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.Company;
import com.lluc.backend.shopapp.shopapp.models.entities.Product;

import java.util.stream.Collectors;

public class DTOMapperCompany {

    private DTOMapperCompany() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Convertir de Company a CompanyDTO
    public static CompanyDTO toDTO(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company is null");
        }

        return new CompanyDTO(
            company.getId(),
            company.getName(),
            company.getDescription(),
            company.getAddress(),
            company.getPhoneNumber(),
            company.getEmail(),
            company.getProducts() != null
                ? company.getProducts().stream()
                    .map(DTOMapperProduct::toProductDTO)
                    .collect(Collectors.toList())
                : null,
            company.getAdministrator(),
            company.getVersion() != null ? company.getVersion() : 0
        );
    }

    // Convertir de CompanyDTO a Company
    public static Company toEntity(CompanyDTO companyDTO) {
        if (companyDTO == null) {
            throw new IllegalArgumentException("CompanyDTO is null");
        }

        Company company = new Company();
        company.setId(companyDTO.getId());
        company.setName(companyDTO.getName());
        company.setDescription(companyDTO.getDescription());
        company.setAddress(companyDTO.getAddress());
        company.setPhoneNumber(companyDTO.getPhoneNumber());
        company.setEmail(companyDTO.getEmail());
        company.setProducts(
            companyDTO.getProducts() != null
                ? companyDTO.getProducts().stream()
                    .map(DTOMapperProduct::toProductEntity)
                    .collect(Collectors.toList())
                : null
        );
        company.setAdministrator(companyDTO.getAdministrator());

        return company;
    }
}