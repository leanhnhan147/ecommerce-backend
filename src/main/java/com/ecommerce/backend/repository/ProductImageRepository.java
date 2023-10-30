package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
