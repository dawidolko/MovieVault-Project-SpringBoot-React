package com.movievault.repository;

import com.movievault.model.MovieDirector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieDirectorRepository extends JpaRepository<MovieDirector, Long> {
    List<MovieDirector> findByMovieId(Long movieId);
    List<MovieDirector> findByPersonId(Long personId);
    void deleteByMovieId(Long movieId);
}
