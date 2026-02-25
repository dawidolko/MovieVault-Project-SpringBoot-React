package com.movievault.seed;

import com.movievault.model.*;
import com.movievault.repository.MovieRepository;
import com.movievault.repository.ReviewRepository;
import com.movievault.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class ReviewSeeder {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public ReviewSeeder(ReviewRepository reviewRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public void seed() {
        if (reviewRepository.count() > 0) {
            System.out.println("Reviews already seeded, skipping.");
            return;
        }

        User critic1 = userRepository.findByEmail("roger@movievault.com").orElse(null);
        User critic2 = userRepository.findByEmail("mark@movievault.com").orElse(null);
        User user1 = userRepository.findByEmail("jan@movievault.com").orElse(null);
        User user2 = userRepository.findByEmail("anna@movievault.com").orElse(null);
        User user3 = userRepository.findByEmail("piotr@movievault.com").orElse(null);
        User user4 = userRepository.findByEmail("maria@movievault.com").orElse(null);
        User user5 = userRepository.findByEmail("tomasz@movievault.com").orElse(null);

        if (critic1 == null || user1 == null) return;

        List<Movie> movies = movieRepository.findAll();
        Random rng = new Random(42);

        String[][] criticTitlesContent = {
            {"A masterpiece of cinema", "This film transcends its genre and delivers a profound experience."},
            {"Exceptional filmmaking", "The direction and performances elevate this to a must-see film."},
            {"Brilliantly crafted", "Every scene serves a purpose in this meticulously constructed narrative."},
            {"A triumph of storytelling", "The screenplay is tight, the pacing is perfect, and the acting is superb."},
            {"Outstanding achievement", "Rarely does a film achieve this level of artistic and commercial success."},
            {"Deeply compelling", "A film that lingers in the mind long after the credits roll."},
            {"Visually stunning", "The cinematography alone makes this worth watching on the biggest screen possible."},
            {"A genre-defining film", "This sets the standard by which all similar films will be measured."},
            {"Bold and uncompromising", "The director makes no concessions to audience comfort, and the film is better for it."},
            {"Near-perfect execution", "While not without minor flaws, this represents filmmaking at its finest."},
        };

        String[][] userTitlesContent = {
            {"Loved it!", "One of my all-time favorites. Highly recommend watching this."},
            {"Amazing movie", "Kept me on the edge of my seat from start to finish."},
            {"Really enjoyed this", "Great acting and a solid story. Will watch again."},
            {"So good!", "Everything about this movie was great. The cast was perfect."},
            {"Must watch", "If you haven't seen this yet, you're missing out."},
            {"Pretty good", "Solid movie overall, though a bit long in parts."},
            {"Great entertainment", "Perfect movie for a weekend viewing session."},
            {"Impressive", "Well made movie with memorable scenes and great music."},
            {"Excellent film", "The storytelling in this one is top notch."},
            {"Very entertaining", "A fun ride from beginning to end. Recommended!"},
            {"Good but not great", "Enjoyable movie but didn't blow me away completely."},
            {"Decent watch", "Not my favorite but still worth the time."},
            {"Above average", "Better than most movies I've seen recently."},
            {"Thoroughly enjoyed it", "Great cast, great story, great execution."},
            {"Solid movie", "Nothing groundbreaking but very well done overall."},
        };

        User[] users = {user1, user2, user3, user4, user5};
        User[] critics = {critic1, critic2};

        int reviewCount = 0;

        for (Movie movie : movies) {
            // Each critic reviews most movies
            for (User critic : critics) {
                if (rng.nextDouble() < 0.75) {
                    int idx = rng.nextInt(criticTitlesContent.length);
                    int rating = 6 + rng.nextInt(5); // 6-10
                    createReview(movie, critic, rating, criticTitlesContent[idx][0], criticTitlesContent[idx][1], true);
                    reviewCount++;
                }
            }

            // 2-4 regular users review each movie
            int numUserReviews = 2 + rng.nextInt(3);
            List<Integer> userIndices = new java.util.ArrayList<>();
            for (int i = 0; i < users.length; i++) userIndices.add(i);
            java.util.Collections.shuffle(userIndices, rng);

            for (int i = 0; i < Math.min(numUserReviews, users.length); i++) {
                User user = users[userIndices.get(i)];
                int idx = rng.nextInt(userTitlesContent.length);
                int rating = 5 + rng.nextInt(6); // 5-10
                createReview(movie, user, rating, userTitlesContent[idx][0], userTitlesContent[idx][1], false);
                reviewCount++;
            }
        }

        // Recalculate all movie ratings
        for (Movie movie : movies) {
            Double avgUser = reviewRepository.getAverageUserRatingByMovieId(movie.getId());
            Double avgCritic = reviewRepository.getAverageCriticRatingByMovieId(movie.getId());
            movie.setAverageUserRating(avgUser != null ? Math.round(avgUser * 10.0) / 10.0 : 0.0);
            movie.setAverageCriticRating(avgCritic != null ? Math.round(avgCritic * 10.0) / 10.0 : 0.0);
            movieRepository.save(movie);
        }

        System.out.println("Review seeding completed: " + reviewCount + " reviews.");
    }

    private void createReview(Movie movie, User user, int rating, String title, String content, boolean isCritic) {
        if (reviewRepository.existsByMovieIdAndUserId(movie.getId(), user.getId())) return;

        Review review = new Review();
        review.setMovie(movie);
        review.setUser(user);
        review.setRating(rating);
        review.setTitle(title);
        review.setContent(content);
        review.setIsCriticReview(isCritic);
        review.setLikesCount(0);
        reviewRepository.save(review);
    }
}
