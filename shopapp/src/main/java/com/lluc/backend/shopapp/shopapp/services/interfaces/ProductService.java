package com.lluc.backend.shopapp.shopapp.services.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lluc.backend.shopapp.shopapp.models.dto.ProductDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.Product;
import com.lluc.backend.shopapp.shopapp.models.request.ProductRequest;

@Service
public interface ProductService {

    // Obtener todos los productos
    List<Product> findAll();

    // Agregar un nuevo producto
    ProductDTO add(ProductRequest product);

    // Obtener un producto por su ID
    ProductDTO getById(Long id);

    // Obtener todos los productos de una empresa específica
    List<ProductDTO> getProductsByCompany(Long companyId);

    // Obtener todos los productos de una categoría específica
    List<ProductDTO> getProductsByCategory(Long categoryId);

    // Modificar un producto existente
    Product update(Long id, Product product);
}