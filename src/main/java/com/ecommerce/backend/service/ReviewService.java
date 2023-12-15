package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.review.ReviewDto;
import com.ecommerce.backend.form.review.CreateReviewForm;
import com.ecommerce.backend.form.review.UpdateStateReviewForm;
import com.ecommerce.backend.storage.criteria.ReviewCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    ReviewDto getReviewById(Long id);

    ResponseListDto<List<ReviewDto>> getList(ReviewCriteria reviewCriteria, Pageable pageable);

    ResponseListDto<List<ReviewDto>> getListReview(ReviewCriteria reviewCriteria, Pageable pageable);

    void createReview(CreateReviewForm createReviewForm, Long customerId);

    void updateStateReview(UpdateStateReviewForm updateStateReviewForm);
}
