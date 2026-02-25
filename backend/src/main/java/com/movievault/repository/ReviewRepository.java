package com.movievault.repository;

import com.movievault.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovieIdOrderByCreatedAtDesc(Long movieId);
    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Review> findByMovieIdAndUserId(Long movieId, Long userId);
    long countByMovieId(Long movieId);
    long countByUserId(Long userId);
    boolean existsByMovieIdAndUserId(Long movieId, Long userId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.movie.id = :movieId AND r.isCriticReview = false")
    Double getAverageUserRatingByMovieId(@Param("movieId") Long movieId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.movie.id = :movieId AND r.isCriticReview = true")
    Double getAverageCriticRatingByMovieId(@Param("movieId") Long movieId);

    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.isCriticReview = true")
    Double getOverallAverageCriticRating();

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.isCriticReview = false")
    Double getOverallAverageUserRating();

    @Query("SELECT r.user.id, r.user.firstName, r.user.lastName, COUNT(r) FROM Review r GROUP BY r.user.id, r.user.firstName, r.user.lastName ORDER BY COUNT(r) DESC")
    List<Object[]> findMostActiveReviewers();
}
