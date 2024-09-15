package com.library.online_library_spring_app.dto.request.create;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RatingAndReviewCreateRequest {
    @NotNull(message = "Book id can not be null")
    private Long bookId;
    @NotNull(message = "User id can not be null")
    private Long userId;
    @NotNull(message = "Rating can not be null")
    private Integer rating;
    private String review;
    private Timestamp createdAt=new Timestamp(System.currentTimeMillis());
}
