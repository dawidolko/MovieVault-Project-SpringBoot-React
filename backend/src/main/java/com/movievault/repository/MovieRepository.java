package com.movievault.repository;

import com.movievault.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId")
    Page<Movie> findByGenreId(@Param("genreId") Long genreId, Pageable pageable);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId AND LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<Movie> findByTitleContainingIgnoreCaseAndGenreId(@Param("title") String title, @Param("genreId") Long genreId, Pageable pageable);

    List<Movie> findTop10ByOrderByAverageUserRatingDesc();

    @Query("SELECT m FROM Movie m ORDER BY m.createdAt DESC")
    List<Movie> findLatest(Pageable pageable);

    @Query("SELECT COUNT(m) FROM Movie m JOIN m.genres g WHERE g.id = :genreId")
    long countByGenreId(@Param("genreId") Long genreId);
}
