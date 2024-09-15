package com.library.online_library_spring_app.dto.response.ratingAndReviewResponse;

import com.library.online_library_spring_app.dao.entity.Books;
import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RatingAndReviewUserResponse {
    private String bookName;
    private Integer rating;
    private String review;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
