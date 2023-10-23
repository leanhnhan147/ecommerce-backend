package com.ecommerce.site.admin.repository;

import com.ecommerce.site.admin.storage.entity.ProductVariationOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariationOptionValueRepository extends JpaRepository<ProductVariationOptionValue, Long> {
}
