package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    @Transactional
    @Modifying
    void deleteAllByProductId(Long productId);
}
