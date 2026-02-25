package com.movievault.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie_cast")
@Data
@NoArgsConstructor
public class MovieCast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(nullable = false)
    private String characterName;

    private Integer castOrder;
}
