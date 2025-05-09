package com.lluc.backend.shopapp.shopapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lluc.backend.shopapp.shopapp.models.entities.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    List<Company> findAll();
    
    Optional<Company> findByName(String name);
    Optional<Company> findByAdministratorUsername(String username);

}
