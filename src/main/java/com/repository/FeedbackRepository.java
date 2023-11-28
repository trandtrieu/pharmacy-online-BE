package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
	// Hoặc sử dụng @Query annotation để viết truy vấn SQL tương tự
    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.rating = :rating AND f.product.id = :productId")
    int countByRatingAndProductId(
        @Param("rating") int rating,
        @Param("productId") int productId
    );
    
    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.product.product_id = :productId")
    int countFeedbacksByProductId(@Param("productId") int productId);
}

