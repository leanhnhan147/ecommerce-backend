package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    Boolean existsByProductIdAndOptionId(Long productId, Long optionId);
}
