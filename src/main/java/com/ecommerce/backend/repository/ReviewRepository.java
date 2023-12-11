package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    Optional<Review> findByCustomerIdAndProductVariationId(Long customerId, Long productVariationId);

    @Query("select r from Review r where r.state = :state and r.productVariation.product in (select p.id from Product p where p.id = :productId)")
    List<Review> findByProductIdAndState(@Param("productId") Long productId, @Param("state") Integer state);
}
