package com.movievault.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private String originalTitle;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private String posterUrl;
    private String trailerUrl;
    private String country;
    private String language;
    private BigDecimal budget;
    private BigDecimal boxOffice;
    private Double averageUserRating;
    private Double averageCriticRating;
    private List<String> genres;
    private List<CastMemberDTO> cast;
    private List<PersonDTO> directors;
    private Long reviewCount;
}
