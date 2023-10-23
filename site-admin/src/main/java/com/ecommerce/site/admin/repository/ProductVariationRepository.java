package com.ecommerce.site.admin.repository;

import com.ecommerce.site.admin.storage.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long>, JpaSpecificationExecutor<ProductVariation> {
}
