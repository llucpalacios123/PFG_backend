package com.lluc.backend.shopapp.shopapp.services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lluc.backend.shopapp.shopapp.models.entities.Product;
import com.lluc.backend.shopapp.shopapp.repositories.ProductRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.ProductService;


@Service
public class ProductServiceImpl implements ProductService{
    
    @Autowired
    private final ProductRepository productRepository;

     public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
}
