package com.lluc.backend.shopapp.shopapp.services.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lluc.backend.shopapp.shopapp.models.Product;

@Service
public interface ProductService {
    
    List<Product> findAll();
        
}
