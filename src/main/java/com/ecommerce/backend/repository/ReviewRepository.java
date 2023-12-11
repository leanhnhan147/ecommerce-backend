package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    Optional<Review> findByCustomerIdAndProductVariationId(Long customerId, Long productVariationId);
}
