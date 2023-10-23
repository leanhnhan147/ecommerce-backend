package com.ecommerce.site.admin.repository;

import com.ecommerce.site.admin.storage.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
