package com.library.online_library_spring_app.controller;

import com.library.online_library_spring_app.dto.request.create.RatingAndReviewCreateRequest;
import com.library.online_library_spring_app.dto.request.update.RatingAndReviewUpdateRequest;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.dto.response.ratingAndReviewResponse.RatingAndReviewBookResponse;
import com.library.online_library_spring_app.dto.response.ratingAndReviewResponse.RatingAndReviewResponse;
import com.library.online_library_spring_app.dto.response.ratingAndReviewResponse.RatingAndReviewUserResponse;
import com.library.online_library_spring_app.service.RatingAndReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//1. addRatingAndReview():+
//    2. getRatingsAndReviewsByBook() :+
//    3. getRatingsAndReviewsByUser(): +
//    4. updateRatingAndReview() :+
//    5. deleteRatingAndReview() :+


@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class RatingAndReviewController {
    private final RatingAndReviewService ratingAndReviewService;
//    @GetMapping
//    private SuccessResponse<List<RatingAndReviewResponse>> getRatingAndReview() {
//        return ratingAndReviewService.getAllRatingAndReviews();
//    }
    @GetMapping("/by-book/{bookName}")
    private SuccessResponse<List<RatingAndReviewBookResponse>> getRatingAndReviewByBook(@PathVariable String bookName )  {
        return ratingAndReviewService.getAllRatingAndReviewsByBook(bookName);
    }
    @GetMapping("/by-user/{userId}")
    private SuccessResponse<List<RatingAndReviewUserResponse>> getRatingAndReviewByUser(@PathVariable Long userId )  {
        return ratingAndReviewService.getAllRatingAndReviewsByUser(userId);
    }
    @PostMapping
    private SuccessResponse<RatingAndReviewResponse> createRatingAndReview(@RequestBody @Valid RatingAndReviewCreateRequest ratingAndReviewCreateRequest) {
        return ratingAndReviewService.createRatingAndReview(ratingAndReviewCreateRequest);
    }
    @PutMapping("/{ratingId}")
    private SuccessResponse<RatingAndReviewResponse> updateRatingAndReview(@RequestBody RatingAndReviewUpdateRequest ratingAndReviewUpdateRequest, @PathVariable Long ratingId) {
        return ratingAndReviewService.updateRatingAndReview(ratingAndReviewUpdateRequest,ratingId);
    }
    @DeleteMapping("/{ratingId}")
    private SuccessResponse<RatingAndReviewResponse> deleteRatingAndReview(@PathVariable Long ratingId ) {
        return ratingAndReviewService.deleteRatingAndReview(ratingId);
    }
}
