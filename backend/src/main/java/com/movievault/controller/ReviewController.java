package com.movievault.controller;

import com.movievault.dto.ReviewDTO;
import com.movievault.model.Movie;
import com.movievault.model.Review;
import com.movievault.model.RoleName;
import com.movievault.model.User;
import com.movievault.repository.MovieRepository;
import com.movievault.repository.ReviewRepository;
import com.movievault.repository.UserRepository;
import com.movievault.service.MovieService;
import com.movievault.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final MovieService movieService;

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository,
                            UserRepository userRepository, MovieRepository movieRepository,
                            MovieService movieService) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.movieService = movieService;
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getMovieReviews(@PathVariable Long movieId) {
        Long currentUserId = getCurrentUserId();
        List<ReviewDTO> reviews = reviewService.findByMovie(movieId).stream()
                .map(r -> reviewService.toDTO(r, currentUserId))
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserReviews(@PathVariable Long userId) {
        Long currentUserId = getCurrentUserId();
        List<ReviewDTO> reviews = reviewService.findByUser(userId).stream()
                .map(r -> reviewService.toDTO(r, currentUserId))
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Map<String, Object> body) {
        User user = getCurrentUser();
        if (user == null) return ResponseEntity.status(401).build();

        Long movieId = ((Number) body.get("movieId")).longValue();
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) return ResponseEntity.badRequest().body(Map.of("error", "Movie not found"));

        try {
            Review review = new Review();
            review.setRating((Integer) body.get("rating"));
            review.setTitle((String) body.get("title"));
            review.setContent((String) body.get("content"));

            Review saved = reviewService.create(review, user, movie);
            movieService.recalculateRatings(movieId);

            return ResponseEntity.ok(reviewService.toDTO(saved, user.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        User user = getCurrentUser();
        if (user == null) return ResponseEntity.status(401).build();

        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null) return ResponseEntity.notFound().build();
        if (!review.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body(Map.of("error", "Not authorized"));
        }

        Review updated = reviewService.update(id,
                body.containsKey("rating") ? (Integer) body.get("rating") : null,
                (String) body.get("title"),
                (String) body.get("content"));

        movieService.recalculateRatings(review.getMovie().getId());
        return ResponseEntity.ok(reviewService.toDTO(updated, user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        User user = getCurrentUser();
        if (user == null) return ResponseEntity.status(401).build();

        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null) return ResponseEntity.notFound().build();

        boolean isOwner = review.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRole().getName() == RoleName.ADMIN;
        if (!isOwner && !isAdmin) {
            return ResponseEntity.status(403).body(Map.of("error", "Not authorized"));
        }

        Long movieId = review.getMovie().getId();
        reviewService.delete(id);
        movieService.recalculateRatings(movieId);

        return ResponseEntity.ok(Map.of("message", "Review deleted"));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> toggleLike(@PathVariable Long id) {
        User user = getCurrentUser();
        if (user == null) return ResponseEntity.status(401).build();

        boolean liked = reviewService.toggleLike(id, user);
        return ResponseEntity.ok(Map.of("liked", liked));
    }

    private User getCurrentUser() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(email).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    private Long getCurrentUserId() {
        User user = getCurrentUser();
        return user != null ? user.getId() : null;
    }
}
