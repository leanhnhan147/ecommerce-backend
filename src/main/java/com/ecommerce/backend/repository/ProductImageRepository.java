package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Transactional
    void deleteAllByMediaResourceId(Long id);
}
