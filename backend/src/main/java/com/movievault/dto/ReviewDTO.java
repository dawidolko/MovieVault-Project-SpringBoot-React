package com.movievault.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userAvatarUrl;
    private Long movieId;
    private String movieTitle;
    private Integer rating;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isCriticReview;
    private Integer likesCount;
    private Boolean likedByCurrentUser;
}
