package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long>, JpaSpecificationExecutor<ReviewImage> {
    List<ReviewImage> findByReviewId(Long reviewId);
}
