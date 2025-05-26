package com.lluc.backend.shopapp.shopapp.services.Implementations;

import com.lluc.backend.shopapp.shopapp.models.entities.Review;
import com.lluc.backend.shopapp.shopapp.repositories.ReviewRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }
}