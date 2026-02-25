package com.movievault.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "watchlist", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "movie_id"})
})
@Data
@NoArgsConstructor
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private LocalDateTime addedAt;

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
    }
}
