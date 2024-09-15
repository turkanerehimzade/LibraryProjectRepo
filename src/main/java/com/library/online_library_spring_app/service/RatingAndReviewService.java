package com.library.online_library_spring_app.service;

import com.library.online_library_spring_app.dao.entity.Books;
import com.library.online_library_spring_app.dao.entity.RatingAndReview;
import com.library.online_library_spring_app.dao.repository.BooksRepository;
import com.library.online_library_spring_app.dao.repository.RatingAndReviewRepository;
import com.library.online_library_spring_app.dto.request.create.RatingAndReviewCreateRequest;
import com.library.online_library_spring_app.dto.request.update.RatingAndReviewUpdateRequest;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.dto.response.ratingAndReviewResponse.RatingAndReviewBookResponse;
import com.library.online_library_spring_app.dto.response.ratingAndReviewResponse.RatingAndReviewResponse;
import com.library.online_library_spring_app.dto.response.ratingAndReviewResponse.RatingAndReviewUserResponse;
import com.library.online_library_spring_app.enums.ResponseCode;
import com.library.online_library_spring_app.mapper.RatingAndReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingAndReviewService {
    private final RatingAndReviewRepository ratingAndReviewRepository;
    private final RatingAndReviewMapper ratingAndReviewMapper;
    private final BooksRepository booksRepository;

//    public SuccessResponse<List<RatingAndReviewResponse>> getAllRatingAndReviews() {
//        List<RatingAndReviewResponse> ratingAndReviewResponseList = ratingAndReviewRepository.findAll().stream().map(ratingAndReviewMapper::toRatingAndReviewResponse).toList();
//        return SuccessResponse.createSuccessResponse(ratingAndReviewResponseList, ResponseCode.SUCCESS);
//    }
    public SuccessResponse<List<RatingAndReviewBookResponse>> getAllRatingAndReviewsByBook(String bookName) {
        List<RatingAndReviewBookResponse> ratingAndReviewResponseList = ratingAndReviewRepository.findByBookBookName(bookName).stream().map(ratingAndReviewMapper::toRatingAndReviewResponseBook).toList();
        return SuccessResponse.createSuccessResponse(ratingAndReviewResponseList, ResponseCode.SUCCESS);
    }
    public SuccessResponse<List<RatingAndReviewUserResponse>> getAllRatingAndReviewsByUser(Long userId) {
        List<RatingAndReviewUserResponse> ratingAndReviewResponseList = ratingAndReviewRepository.findByUserId(userId).stream().map(ratingAndReviewMapper::toRatingAndReviewResponseUser).toList();
        return SuccessResponse.createSuccessResponse(ratingAndReviewResponseList, ResponseCode.SUCCESS);
    }
    public SuccessResponse<RatingAndReviewResponse> createRatingAndReview(RatingAndReviewCreateRequest ratingAndReviewCreateRequest){
        Books book = booksRepository.findById(ratingAndReviewCreateRequest.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        RatingAndReview ratingAndReview=ratingAndReviewMapper.ToRatingAndReview(ratingAndReviewCreateRequest);
        ratingAndReview.setBook(book);
        ratingAndReviewRepository.save(ratingAndReview);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }
    public SuccessResponse<RatingAndReviewResponse> updateRatingAndReview(RatingAndReviewUpdateRequest ratingAndReviewUpdateRequest,Long ratingId){
        RatingAndReview ratingAndReview=ratingAndReviewRepository.findById(ratingId).orElseThrow();
        buildRatingAndReview(ratingAndReview,ratingAndReviewUpdateRequest);
        ratingAndReviewRepository.save(ratingAndReview);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);

    }
    public static void buildRatingAndReview(RatingAndReview ratingAndReview,RatingAndReviewUpdateRequest ratingAndReviewUpdateRequest) {
        if(ratingAndReviewUpdateRequest.getRating()!=null){
            ratingAndReview.setRating(ratingAndReviewUpdateRequest.getRating());
        }
        if(ratingAndReviewUpdateRequest.getReview()!=null){
            ratingAndReview.setReview(ratingAndReviewUpdateRequest.getReview());
        }
    }
    public SuccessResponse<RatingAndReviewResponse> deleteRatingAndReview(Long ratingId){
        RatingAndReview ratingAndReview=ratingAndReviewRepository.findById(ratingId).orElseThrow();
        ratingAndReviewRepository.delete(ratingAndReview);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }
}
