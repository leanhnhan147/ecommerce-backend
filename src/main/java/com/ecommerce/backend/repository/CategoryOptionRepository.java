package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.CategoryOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryOptionRepository extends JpaRepository<CategoryOption, Long> {
    @Transactional
    @Modifying
    void deleteAllByOptionId(Long optionId);
}
