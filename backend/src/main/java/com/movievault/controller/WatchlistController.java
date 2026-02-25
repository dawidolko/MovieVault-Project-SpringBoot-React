package com.movievault.controller;

import com.movievault.dto.WatchlistDTO;
import com.movievault.model.User;
import com.movievault.model.Watchlist;
import com.movievault.repository.UserRepository;
import com.movievault.service.WatchlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final UserRepository userRepository;

    public WatchlistController(WatchlistService watchlistService, UserRepository userRepository) {
        this.watchlistService = watchlistService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getWatchlist() {
        User user = getCurrentUser();
        if (user == null) return ResponseEntity.status(401).build();

        List<WatchlistDTO> items = watchlistService.getByUser(user.getId()).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }

    @PostMapping("/{movieId}")
    public ResponseEntity<?> addToWatchlist(@PathVariable Long movieId) {
        User user = getCurrentUser();
        if (user == null) return ResponseEntity.status(401).build();

        try {
            Watchlist w = watchlistService.add(user.getId(), movieId);
            return ResponseEntity.ok(toDTO(w));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> removeFromWatchlist(@PathVariable Long movieId) {
        User user = getCurrentUser();
        if (user == null) return ResponseEntity.status(401).build();

        watchlistService.remove(user.getId(), movieId);
        return ResponseEntity.ok(Map.of("message", "Removed from watchlist"));
    }

    @GetMapping("/check/{movieId}")
    public ResponseEntity<?> checkWatchlist(@PathVariable Long movieId) {
        User user = getCurrentUser();
        if (user == null) return ResponseEntity.ok(Map.of("inWatchlist", false));

        boolean inWatchlist = watchlistService.isInWatchlist(user.getId(), movieId);
        return ResponseEntity.ok(Map.of("inWatchlist", inWatchlist));
    }

    private User getCurrentUser() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(email).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    private WatchlistDTO toDTO(Watchlist w) {
        return new WatchlistDTO(
                w.getId(), w.getMovie().getId(), w.getMovie().getTitle(),
                w.getMovie().getPosterUrl(), w.getMovie().getAverageUserRating(),
                w.getAddedAt()
        );
    }
}
