package com.library.online_library_spring_app.dto.response.ratingAndReviewResponse;

import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RatingAndReviewBookResponse {
    private Long userId;
    private Integer rating;
    private String review;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
