package com.movievault.repository;

import com.movievault.model.MovieCast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieCastRepository extends JpaRepository<MovieCast, Long> {
    List<MovieCast> findByMovieIdOrderByCastOrderAsc(Long movieId);
    List<MovieCast> findByPersonId(Long personId);
    void deleteByMovieId(Long movieId);
}
