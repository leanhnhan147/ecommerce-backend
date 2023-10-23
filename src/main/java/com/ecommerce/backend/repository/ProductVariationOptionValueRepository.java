package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.ProductVariationOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariationOptionValueRepository extends JpaRepository<ProductVariationOptionValue, Long> {
}
