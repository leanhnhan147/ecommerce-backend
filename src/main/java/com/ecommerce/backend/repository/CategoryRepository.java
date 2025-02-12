package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    Category findByNameAndLevel(String name, Integer level);
    Category findByNameAndLevelAndParentId(String name, Integer level, Long parentId);
    Category findByCode(String code);
    Category findByIdAndCode(Long id, String code);

    @Query("SELECT c.id FROM Category c WHERE c.parent.id = :parentId")
    List<Long> getAllCategoryIdByParentId(@Param("parentId") Long parentId);
}
