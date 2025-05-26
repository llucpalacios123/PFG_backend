package com.lluc.backend.shopapp.shopapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lluc.backend.shopapp.shopapp.models.entities.Product;
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAll();
    // Buscar productos por empresa
    List<Product> findByCompanyId(Long companyId);

    // Buscar productos por categoría
    List<Product> findByCategoryId(Long categoryId);
    
    @Query("""
        SELECT DISTINCT p FROM Product p 
        JOIN p.translations t 
        LEFT JOIN p.sustainableCategories sc 
        LEFT JOIN p.category c
        WHERE (LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%')) 
        OR LOWER(t.description) LIKE LOWER(CONCAT('%', :query, '%')))
        AND (
            (:sustainableCategoriesEmpty = true OR sc.id IN :sustainableCategories)
        )
        AND (
            (:categoriesEmpty = true OR c.id IN :categories)
        )
    """)
    Page<Product> findProductFiltereds(
        @Param("query") String query,
        @Param("sustainableCategories") List<Long> sustainableCategories,
        @Param("sustainableCategoriesEmpty") boolean sustainableCategoriesEmpty,
        @Param("categories") List<Long> categories,
        @Param("categoriesEmpty") boolean categoriesEmpty,
        Pageable pageable
    );
    
    @Query("""
    SELECT DISTINCT p FROM Product p
    JOIN p.category c
    LEFT JOIN p.sustainableCategories sc
    WHERE c.id = :categoryId
    AND (:sustainableCategoryIds IS NULL OR sc.id IN :sustainableCategoryIds)
    """)
    List<Product> findByCategoryAndSustainableCategories(
        @Param("categoryId") Long categoryId,
        @Param("sustainableCategoryIds") List<Long> sustainableCategoryIds
    );
}