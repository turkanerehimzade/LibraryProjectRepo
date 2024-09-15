package com.library.online_library_spring_app.dto.request.update;

import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RatingAndReviewUpdateRequest {
    private Integer rating;
    private String review;
    private Timestamp updatedAt=new Timestamp(System.currentTimeMillis());
}
