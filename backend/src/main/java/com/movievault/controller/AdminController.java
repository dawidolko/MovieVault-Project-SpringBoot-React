package com.movievault.controller;

import com.movievault.dto.MovieDTO;
import com.movievault.model.RoleName;
import com.movievault.model.User;
import com.movievault.repository.RoleRepository;
import com.movievault.repository.UserRepository;
import com.movievault.service.AnalyticsService;
import com.movievault.service.MovieService;
import com.movievault.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AnalyticsService analyticsService;
    private final MovieService movieService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(AnalyticsService analyticsService, MovieService movieService,
                           UserRepository userRepository, UserService userService,
                           RoleRepository roleRepository) {
        this.analyticsService = analyticsService;
        this.movieService = movieService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/analytics/overview")
    public ResponseEntity<?> getOverview() {
        Map<String, Long> overview = new HashMap<>();
        overview.put("totalMovies", analyticsService.getTotalMovies());
        overview.put("totalUsers", analyticsService.getTotalUsers());
        overview.put("totalReviews", analyticsService.getTotalReviews());
        return ResponseEntity.ok(overview);
    }

    @GetMapping("/analytics/top-movies")
    public ResponseEntity<?> getTopMovies() {
        List<MovieDTO> movies = movieService.getFeatured().stream()
                .map(movieService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/analytics/reviews-per-month")
    public ResponseEntity<?> getReviewsPerMonth() {
        return ResponseEntity.ok(analyticsService.getReviewsPerMonth());
    }

    @GetMapping("/analytics/genre-distribution")
    public ResponseEntity<?> getGenreDistribution() {
        return ResponseEntity.ok(analyticsService.getGenreDistribution());
    }

    @GetMapping("/analytics/user-registrations")
    public ResponseEntity<?> getUserRegistrations() {
        return ResponseEntity.ok(analyticsService.getUserRegistrationsPerMonth());
    }

    @GetMapping("/analytics/critic-vs-user")
    public ResponseEntity<?> getCriticVsUser() {
        return ResponseEntity.ok(analyticsService.getCriticVsUserAvgRatings());
    }

    @GetMapping("/analytics/most-active-reviewers")
    public ResponseEntity<?> getMostActiveReviewers() {
        return ResponseEntity.ok(analyticsService.getMostActiveReviewers());
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return ResponseEntity.ok(users.map(userService::toDTO));
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> changeRole(@PathVariable Long id, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();

        String roleName = body.get("roleName");
        try {
            RoleName rn = RoleName.valueOf(roleName);
            roleRepository.findByName(rn).ifPresent(role -> {
                user.setRole(role);
                userRepository.save(user);
            });
            return ResponseEntity.ok(userService.toDTO(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid role name"));
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "User deleted"));
    }
}
