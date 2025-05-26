package com.lluc.backend.shopapp.shopapp.repositories;

import com.lluc.backend.shopapp.shopapp.models.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}