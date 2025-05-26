package com.lluc.backend.shopapp.shopapp.controllers;

import com.lluc.backend.shopapp.shopapp.models.entities.Category;
import com.lluc.backend.shopapp.shopapp.models.entities.SustainableCategory;
import com.lluc.backend.shopapp.shopapp.services.interfaces.CategoryService;
import com.lluc.backend.shopapp.shopapp.services.interfaces.SustainableCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SustainableCategoryService sustainableCategoryService;

    // Endpoint para devolver solo las categorías
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    // Endpoint para devolver solo las categorías sostenibles
    @GetMapping("/sustainable")
    public ResponseEntity<List<SustainableCategory>> getAllSustainableCategories() {
        List<SustainableCategory> sustainableCategories = sustainableCategoryService.getSustainableCategories();
        return ResponseEntity.ok(sustainableCategories);
    }

    // Endpoint para devolver ambas listas (categorías y categorías sostenibles)
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCategoriesAndSustainableCategories() {
        List<Category> categories = categoryService.getCategories();
        List<SustainableCategory> sustainableCategories = sustainableCategoryService.getSustainableCategories();

        Map<String, Object> response = new HashMap<>();
        response.put("categories", categories);
        response.put("sustainableCategories", sustainableCategories);

        return ResponseEntity.ok(response);
    }
}