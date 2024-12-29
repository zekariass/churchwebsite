package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.ProductReview;
import com.churchwebsite.churchwebsite.repositories.Shopping.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewService {

    @Autowired
    private ProductReviewRepository productReviewRepository;

    public List<ProductReview> getAllReviews() {
        return productReviewRepository.findAll();
    }

    public Optional<ProductReview> getReviewById(Integer id) {
        return productReviewRepository.findById(id);
    }

    public ProductReview saveReview(ProductReview review) {
        return productReviewRepository.save(review);
    }

    public ProductReview updateReview(Integer id, ProductReview updatedReview) {
        return productReviewRepository.findById(id)
                .map(existingReview -> {
                    existingReview.setProduct(updatedReview.getProduct());
                    existingReview.setUser(updatedReview.getUser());
                    existingReview.setRating(updatedReview.getRating());
                    existingReview.setComment(updatedReview.getComment());
                    return productReviewRepository.save(existingReview);
                }).orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public void deleteReview(Integer id) {
        productReviewRepository.deleteById(id);
    }
}

