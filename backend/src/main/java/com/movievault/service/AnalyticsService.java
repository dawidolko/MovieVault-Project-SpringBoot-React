package com.movievault.service;

import com.movievault.model.GenreName;
import com.movievault.model.User;
import com.movievault.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AnalyticsService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final GenreRepository genreRepository;

    public AnalyticsService(MovieRepository movieRepository, UserRepository userRepository,
                            ReviewRepository reviewRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.genreRepository = genreRepository;
    }

    public long getTotalMovies() {
        return movieRepository.count();
    }

    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getTotalReviews() {
        return reviewRepository.count();
    }

    public Map<String, Long> getReviewsPerMonth() {
        Map<String, Long> result = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (int i = 11; i >= 0; i--) {
            YearMonth ym = YearMonth.now().minusMonths(i);
            LocalDateTime start = ym.atDay(1).atStartOfDay();
            LocalDateTime end = ym.atEndOfMonth().atTime(23, 59, 59);
            long count = reviewRepository.countByCreatedAtBetween(start, end);
            result.put(ym.format(formatter), count);
        }
        return result;
    }

    public Map<String, Long> getGenreDistribution() {
        Map<String, Long> result = new LinkedHashMap<>();
        for (GenreName genre : GenreName.values()) {
            genreRepository.findByName(genre).ifPresent(g -> {
                long count = movieRepository.countByGenreId(g.getId());
                if (count > 0) {
                    result.put(genre.name(), count);
                }
            });
        }
        return result;
    }

    public Map<String, Long> getUserRegistrationsPerMonth() {
        Map<String, Long> result = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        List<User> allUsers = userRepository.findAll();

        for (int i = 11; i >= 0; i--) {
            YearMonth ym = YearMonth.now().minusMonths(i);
            String key = ym.format(formatter);
            long count = allUsers.stream()
                    .filter(u -> u.getCreatedAt() != null)
                    .filter(u -> YearMonth.from(u.getCreatedAt()).equals(ym))
                    .count();
            result.put(key, count);
        }
        return result;
    }

    public Map<String, Double> getCriticVsUserAvgRatings() {
        Map<String, Double> result = new HashMap<>();
        Double criticAvg = reviewRepository.getOverallAverageCriticRating();
        Double userAvg = reviewRepository.getOverallAverageUserRating();
        result.put("critic", criticAvg != null ? Math.round(criticAvg * 10.0) / 10.0 : 0.0);
        result.put("user", userAvg != null ? Math.round(userAvg * 10.0) / 10.0 : 0.0);
        return result;
    }

    public List<Map<String, Object>> getMostActiveReviewers() {
        List<Object[]> rows = reviewRepository.findMostActiveReviewers();
        List<Map<String, Object>> result = new ArrayList<>();
        int limit = Math.min(rows.size(), 10);
        for (int i = 0; i < limit; i++) {
            Object[] row = rows.get(i);
            Map<String, Object> entry = new HashMap<>();
            entry.put("userId", row[0]);
            entry.put("firstName", row[1]);
            entry.put("lastName", row[2]);
            entry.put("reviewCount", row[3]);
            result.add(entry);
        }
        return result;
    }
}
