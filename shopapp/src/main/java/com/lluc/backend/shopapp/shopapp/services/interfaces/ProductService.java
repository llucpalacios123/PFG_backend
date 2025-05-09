package com.lluc.backend.shopapp.shopapp.services.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lluc.backend.shopapp.shopapp.models.entities.Product;
import com.lluc.backend.shopapp.shopapp.models.request.ProductRequest;

@Service
public interface ProductService {

    // Obtener todos los productos
    List<Product> findAll();

    // Agregar un nuevo producto
    Product add(ProductRequest product);

    // Obtener un producto por su ID
    Product getById(Long id);

    // Obtener todos los productos de una empresa específica
    List<Product> getProductsByCompany(Long companyId);

    // Obtener todos los productos de una categoría específica
    List<Product> getProductsByCategory(Long categoryId);

    // Modificar un producto existente
    Product update(Long id, Product product);
}