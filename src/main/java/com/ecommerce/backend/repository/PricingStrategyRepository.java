package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.PricingStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PricingStrategyRepository extends JpaRepository<PricingStrategy, Long>, JpaSpecificationExecutor<PricingStrategy> {
}
