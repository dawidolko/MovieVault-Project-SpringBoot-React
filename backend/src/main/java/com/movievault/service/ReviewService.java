package com.movievault.service;

import com.movievault.dto.ReviewDTO;
import com.movievault.model.*;
import com.movievault.repository.ReviewLikeRepository;
import com.movievault.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public ReviewService(ReviewRepository reviewRepository, ReviewLikeRepository reviewLikeRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewLikeRepository = reviewLikeRepository;
    }

    public List<Review> findByMovie(Long movieId) {
        return reviewRepository.findByMovieIdOrderByCreatedAtDesc(movieId);
    }

    public List<Review> findByUser(Long userId) {
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Review create(Review review, User user, Movie movie) {
        if (reviewRepository.existsByMovieIdAndUserId(movie.getId(), user.getId())) {
            throw new RuntimeException("You already reviewed this movie");
        }
        review.setUser(user);
        review.setMovie(movie);
        review.setIsCriticReview(user.getRole().getName() == RoleName.CRITIC);
        return reviewRepository.save(review);
    }

    public Review update(Long id, Integer rating, String title, String content) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (rating != null) review.setRating(rating);
        if (title != null) review.setTitle(title);
        if (content != null) review.setContent(content);
        return reviewRepository.save(review);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public boolean toggleLike(Long reviewId, User user) {
        var existing = reviewLikeRepository.findByReviewIdAndUserId(reviewId, user.getId());
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (existing.isPresent()) {
            reviewLikeRepository.delete(existing.get());
            review.setLikesCount(Math.max(0, review.getLikesCount() - 1));
            reviewRepository.save(review);
            return false;
        } else {
            ReviewLike like = new ReviewLike();
            like.setReview(review);
            like.setUser(user);
            reviewLikeRepository.save(like);
            review.setLikesCount(review.getLikesCount() + 1);
            reviewRepository.save(review);
            return true;
        }
    }

    public ReviewDTO toDTO(Review review, Long currentUserId) {
        boolean liked = currentUserId != null &&
                reviewLikeRepository.existsByReviewIdAndUserId(review.getId(), currentUserId);

        return new ReviewDTO(
                review.getId(),
                review.getUser().getId(),
                review.getUser().getFirstName(),
                review.getUser().getLastName(),
                review.getUser().getAvatarUrl(),
                review.getMovie().getId(),
                review.getMovie().getTitle(),
                review.getRating(),
                review.getTitle(),
                review.getContent(),
                review.getCreatedAt(),
                review.getUpdatedAt(),
                review.getIsCriticReview(),
                review.getLikesCount(),
                liked
        );
    }
}
