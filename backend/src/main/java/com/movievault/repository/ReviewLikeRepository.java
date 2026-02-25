package com.movievault.repository;

import com.movievault.model.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    Optional<ReviewLike> findByReviewIdAndUserId(Long reviewId, Long userId);
    boolean existsByReviewIdAndUserId(Long reviewId, Long userId);
    long countByReviewId(Long reviewId);
}
