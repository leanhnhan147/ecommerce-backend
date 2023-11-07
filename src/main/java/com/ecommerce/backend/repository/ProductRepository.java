package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Boolean existsByCategoryId(Long categoryId);
}
