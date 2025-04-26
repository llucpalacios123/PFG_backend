package com.lluc.backend.shopapp.shopapp.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lluc.backend.shopapp.shopapp.models.entities.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {
    List<Company> findAll();
    
}
