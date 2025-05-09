package com.lluc.backend.shopapp.shopapp.services.Implementations;

import com.lluc.backend.shopapp.shopapp.models.entities.SustainableCategory;
import com.lluc.backend.shopapp.shopapp.repositories.SustainableCategoryRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.SustainableCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SustainableCategoryServiceImpl implements SustainableCategoryService {

    @Autowired
    private SustainableCategoryRepository sustainableCategoryRepository;

    @Override
    public List<SustainableCategory> getSustainableCategories() {
        return sustainableCategoryRepository.findAll();
    }
}