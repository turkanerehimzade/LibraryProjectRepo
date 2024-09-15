package com.library.online_library_spring_app.dao.repository;

import com.library.online_library_spring_app.dao.entity.RatingAndReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingAndReviewRepository extends JpaRepository<RatingAndReview, Long> {
   List<RatingAndReview> findByBookBookName(String bookName);
    List<RatingAndReview> findByUserId(Long userId);
}
