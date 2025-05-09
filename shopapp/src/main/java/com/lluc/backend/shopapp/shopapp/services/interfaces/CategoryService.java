package com.lluc.backend.shopapp.shopapp.services.interfaces;

import com.lluc.backend.shopapp.shopapp.models.entities.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
}