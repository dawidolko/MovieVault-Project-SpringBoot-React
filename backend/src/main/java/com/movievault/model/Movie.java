package com.movievault.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String originalTitle;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate releaseDate;

    private Integer duration;

    private String posterUrl;

    private String trailerUrl;

    private String country;

    private String language;

    private BigDecimal budget;

    private BigDecimal boxOffice;

    @Column(columnDefinition = "DOUBLE DEFAULT 0")
    private Double averageUserRating = 0.0;

    @Column(columnDefinition = "DOUBLE DEFAULT 0")
    private Double averageCriticRating = 0.0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "movie_genre",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
