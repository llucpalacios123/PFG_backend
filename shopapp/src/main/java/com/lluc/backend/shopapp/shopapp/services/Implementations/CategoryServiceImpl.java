package com.lluc.backend.shopapp.shopapp.services.Implementations;

import com.lluc.backend.shopapp.shopapp.models.entities.Category;
import com.lluc.backend.shopapp.shopapp.repositories.CategoryRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository; 

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}