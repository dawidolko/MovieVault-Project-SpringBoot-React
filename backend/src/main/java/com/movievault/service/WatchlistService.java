package com.movievault.service;

import com.movievault.model.Movie;
import com.movievault.model.User;
import com.movievault.model.Watchlist;
import com.movievault.repository.MovieRepository;
import com.movievault.repository.UserRepository;
import com.movievault.repository.WatchlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public WatchlistService(WatchlistRepository watchlistRepository,
                            UserRepository userRepository, MovieRepository movieRepository) {
        this.watchlistRepository = watchlistRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public List<Watchlist> getByUser(Long userId) {
        return watchlistRepository.findByUserIdOrderByAddedAtDesc(userId);
    }

    public Watchlist add(Long userId, Long movieId) {
        if (watchlistRepository.existsByUserIdAndMovieId(userId, movieId)) {
            throw new RuntimeException("Movie already in watchlist");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        watchlist.setMovie(movie);
        return watchlistRepository.save(watchlist);
    }

    @Transactional
    public void remove(Long userId, Long movieId) {
        watchlistRepository.deleteByUserIdAndMovieId(userId, movieId);
    }

    public boolean isInWatchlist(Long userId, Long movieId) {
        return watchlistRepository.existsByUserIdAndMovieId(userId, movieId);
    }
}
