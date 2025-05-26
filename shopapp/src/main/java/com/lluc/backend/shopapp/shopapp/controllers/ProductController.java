package com.lluc.backend.shopapp.shopapp.controllers;

import com.lluc.backend.shopapp.shopapp.models.dto.ProductDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.mapper.ProductMapper;
import com.lluc.backend.shopapp.shopapp.models.entities.Product;
import com.lluc.backend.shopapp.shopapp.models.entities.Report;
import com.lluc.backend.shopapp.shopapp.models.entities.Review;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.models.request.ProductRequest;
import com.lluc.backend.shopapp.shopapp.models.request.ProductSearchRequest;
import com.lluc.backend.shopapp.shopapp.models.request.ReportRequest;
import com.lluc.backend.shopapp.shopapp.models.request.ReviewRequest;
import com.lluc.backend.shopapp.shopapp.services.interfaces.ProductService;
import com.lluc.backend.shopapp.shopapp.services.interfaces.ReportService;
import com.lluc.backend.shopapp.shopapp.services.interfaces.ReviewService;
import com.lluc.backend.shopapp.shopapp.services.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/products")
@CrossOrigin(originPatterns = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Obtener los productos de la compañía
        List<ProductDTO> products = productService.getProductsByCompany(username);
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
                searchRequest.getCategories(), // Pasar las categorías al servicio
                pageable
        );
    
        return ResponseEntity.ok(products);
    }

    @PostMapping("/reviews")
    public ResponseEntity<?> addReview(@RequestBody ReviewRequest reviewRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username).orElseThrow();

        // Crear la entidad Review
        Review review = new Review();
        review.setProduct(ProductMapper.INSTANCE.toProductEntity(productService.getById(reviewRequest.getProductId()))); // Usar MapStruct
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        review.setUser(user); // Establecer el usuario autenticado como el creador de la review

        // Guardar la review
        reviewService.addReview(review);
        return ResponseEntity.ok("Review added successfully");
    }

    @PostMapping("/reports")
    public ResponseEntity<?> addReport(@RequestBody ReportRequest reportRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username).orElseThrow();

        // Crear la entidad Report
        Report report = new Report();
        report.setProduct(ProductMapper.INSTANCE.toProductEntity(productService.getById(reportRequest.getProductId()))); // Usar MapStruct
        report.setComment(reportRequest.getComment());
        report.setUser(user); // Establecer el usuario autenticado como el creador del reporte

        // Guardar el reporte
        reportService.addReport(report);
        return ResponseEntity.ok("Report added successfully");
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryAndSustainableCategories(
        @PathVariable Long categoryId,
        @RequestParam(required = false) List<Long> sustainableCategoryIds
    ) {
        List<ProductDTO> products = productService.getProductsByCategoryAndSustainableCategories(categoryId, sustainableCategoryIds);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{productId}/stock")
    public ResponseEntity<?> updateProductStock(
            @PathVariable Long productId,
            @RequestBody Map<String, Integer> updatedStock) {
        try {
            productService.updateProductStock(productId, updatedStock);
            return ResponseEntity.ok("Stock actualizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar el stock: " + e.getMessage());
        }
    }

    @PutMapping("/{productId}/activate")
    public ResponseEntity<?> setProductActiveStatus(
            @PathVariable Long productId,
            @RequestBody Map<String, Boolean> activeStatus) {
        try {
            // Obtener el valor de "active" del cuerpo de la solicitud
            boolean isActive = activeStatus.getOrDefault("active", false);
    
            // Llamar al servicio para actualizar el estado del producto
            productService.setProductActiveStatus(productId, isActive);
    
            String message = isActive ? "Producto activado correctamente." : "Producto desactivado correctamente.";
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar el estado del producto: " + e.getMessage());
        }
    }
    

}