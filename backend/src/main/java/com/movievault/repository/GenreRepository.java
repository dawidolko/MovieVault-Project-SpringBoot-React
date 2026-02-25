package com.movievault.repository;

import com.movievault.model.Genre;
import com.movievault.model.GenreName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(GenreName name);
}
