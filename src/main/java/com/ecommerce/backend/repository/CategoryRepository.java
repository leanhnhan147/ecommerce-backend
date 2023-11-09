package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    Category findByNameAndLevel(String name, Integer level);
    Category findByNameAndLevelAndParentId(String name, Integer level, Long parentId);
    Category findByCode(String code);
}
