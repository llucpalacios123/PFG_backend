package com.lluc.backend.shopapp.shopapp.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lluc.backend.shopapp.shopapp.models.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
    
}
