package com.lluc.backend.shopapp.shopapp.repositories;

import com.lluc.backend.shopapp.shopapp.models.entities.SustainableCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SustainableCategoryRepository extends JpaRepository<SustainableCategory, Long> {
    // JpaRepository ya incluye el método findAll, no es necesario implementarlo.
}