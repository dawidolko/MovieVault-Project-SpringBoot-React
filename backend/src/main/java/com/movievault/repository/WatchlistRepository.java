package com.movievault.repository;

import com.movievault.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByUserIdOrderByAddedAtDesc(Long userId);
    Optional<Watchlist> findByUserIdAndMovieId(Long userId, Long movieId);
    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
    void deleteByUserIdAndMovieId(Long userId, Long movieId);
}
