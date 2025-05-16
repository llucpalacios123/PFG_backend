package com.lluc.backend.shopapp.shopapp.controllers;

import com.lluc.backend.shopapp.shopapp.models.dto.ProductDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.Product;
import com.lluc.backend.shopapp.shopapp.models.request.ProductRequest;
import com.lluc.backend.shopapp.shopapp.models.request.ProductSearchRequest;
import com.lluc.backend.shopapp.shopapp.services.interfaces.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(originPatterns = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    // Agregar un nuevo producto
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest product) {
        System.out.println("Producto recibido: " + product);
        productService.add(product);
        return ResponseEntity.ok("Producto recibido correctamente");
    }
    // Obtener un producto por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getById(id);
        return ResponseEntity.ok(product);
    }

    // Obtener todos los productos de una empresa específica
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCompany(@PathVariable Long companyId) {
        List<ProductDTO> products = productService.getProductsByCompany(companyId);
        return ResponseEntity.ok(products);
    }

    // Obtener todos los productos de una categoría específica
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    // Modificar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.update(id, product);
        return ResponseEntity.ok(updatedProduct);
    }
    @PostMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestBody ProductSearchRequest searchRequest) {
        // Crear el objeto Pageable
        Pageable pageable = PageRequest.of(
                searchRequest.getPage(),
                searchRequest.getSize(),
                Sort.by(Sort.Order.by(searchRequest.getSort().split(",")[0])
                        .with(Sort.Direction.fromString(searchRequest.getSort().split(",")[1])))
        );

        // Llamar al servicio con paginación
        Page<ProductDTO> products = productService.searchProducts(
                searchRequest.getQuery(),
                searchRequest.getSustainableCategories(),
                pageable
        );

        return ResponseEntity.ok(products);
    }
}