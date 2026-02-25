package com.movievault.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistDTO {
    private Long id;
    private Long movieId;
    private String movieTitle;
    private String posterUrl;
    private Double averageUserRating;
    private LocalDateTime addedAt;
}
