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
        WHERE (LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%')) 
        OR LOWER(t.description) LIKE LOWER(CONCAT('%', :query, '%')))
        AND (
            :sustainableCategories IS NULL 
            OR sc.id IN :sustainableCategories
        )
    """)
    Page<Product> findProductFiltereds( 
        @Param("query") String query,
        @Param("sustainableCategories") List<Long> sustainableCategories,
        Pageable pageable
    );
}