package com.lluc.backend.shopapp.shopapp.models.dto.mapper;

import com.lluc.backend.shopapp.shopapp.models.dto.ProductDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.ProductTranslationDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.ReportDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.ReviewDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.Product;
import com.lluc.backend.shopapp.shopapp.models.entities.ProductTranslation;
import com.lluc.backend.shopapp.shopapp.models.entities.Report;
import com.lluc.backend.shopapp.shopapp.models.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {PricingMapper.class})
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Mapear de Product a ProductDTO
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "sustainableCategoryIds", expression = "java(product.getSustainableCategories().stream().map(c -> c.getId()).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "companyId", source = "company.id")
    ProductDTO toProductDTO(Product product);

    // Mapear de ProductDTO a Product
    @Mapping(target = "category", ignore = true) // Ignorar la categoría porque solo se pasa el ID
    @Mapping(target = "sustainableCategories", ignore = true) // Ignorar las categorías sostenibles porque solo se pasan los IDs
    @Mapping(target = "company", ignore = true) // Ignorar la compañía porque solo se pasa el ID
    Product toProductEntity(ProductDTO productDTO);

    // Mapear de ProductTranslation a ProductTranslationDTO
    ProductTranslationDTO toProductTranslationDTO(ProductTranslation translation);

    // Mapear de ProductTranslationDTO a ProductTranslation
    ProductTranslation toProductTranslationEntity(ProductTranslationDTO translationDTO);

    // Mapear de Review a ReviewDTO
    @Mapping(target = "userId", source = "user.id")
    ReviewDTO toReviewDTO(Review review);

    // Mapear de Report a ReportDTO
    @Mapping(target = "userId", source = "user.id")
    ReportDTO toReportDTO(Report report);

    // Mapear listas de Product a listas de ProductDTO
    List<ProductDTO> toProductDTOList(List<Product> products);

    // Mapear listas de ProductDTO a listas de Product
    List<Product> toProductEntityList(List<ProductDTO> productDTOs);
}