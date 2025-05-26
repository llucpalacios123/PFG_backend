package com.lluc.backend.shopapp.shopapp.services.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
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
    List<ProductDTO> getProductsByCompany(String username);

    // Obtener todos los productos de una categoría específica
    List<ProductDTO> getProductsByCategory(Long categoryId);

    // Modificar un producto existente
    Product update(Long id, Product product);

    Page<ProductDTO> searchProducts(String query, List<Long> sustainableCategories, List<Long> categories, Pageable pageable);

    List<ProductDTO> getProductsByCategoryAndSustainableCategories(Long categoryId, List<Long> sustainableCategoryIds);

    public void updateStock(Product product, String category, int quantity);

    public void updateProductStock(Long productId, Map<String, Integer> updatedStock);

    void setProductActiveStatus(Long productId, boolean isActive);
}