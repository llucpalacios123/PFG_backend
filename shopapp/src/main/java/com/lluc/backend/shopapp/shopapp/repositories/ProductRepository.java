package com.lluc.backend.shopapp.shopapp.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lluc.backend.shopapp.shopapp.models.entities.Product;
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAll();
    // Buscar productos por empresa
    List<Product> findByCompanyId(Long companyId);

    // Buscar productos por categoría
    List<Product> findByCategoryId(Long categoryId);
}