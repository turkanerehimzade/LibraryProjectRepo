package com.library.online_library_spring_app.mapper;

import com.library.online_library_spring_app.dao.entity.RatingAndReview;
import com.library.online_library_spring_app.dto.request.create.RatingAndReviewCreateRequest;
import com.library.online_library_spring_app.dto.response.ratingAndReviewResponse.RatingAndReviewBookResponse;
import com.library.online_library_spring_app.dto.response.ratingAndReviewResponse.RatingAndReviewUserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RatingAndReviewMapper {
    RatingAndReviewBookResponse toRatingAndReviewResponseBook(RatingAndReview ratingAndReview);
    RatingAndReviewUserResponse toRatingAndReviewResponseUser(RatingAndReview ratingAndReview);
    RatingAndReview ToRatingAndReview(RatingAndReviewCreateRequest ratingAndReviewCreateRequest);
}
