package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.category.CategoryAdminDto;
import com.ecommerce.backend.dto.category.CategoryDto;
import com.ecommerce.backend.form.category.CreateCategoryForm;
import com.ecommerce.backend.form.category.UpdateCategoryForm;
import com.ecommerce.backend.storage.criteria.CategoryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    CategoryAdminDto getCategoryByIdOrCode(Long id, String code);

    ResponseListDto<List<CategoryAdminDto>> getCategoryList(CategoryCriteria categoryCriteria, Pageable pageable);

    List<CategoryDto> getCategoryListAutoComplete(CategoryCriteria categoryCriteria);

    CategoryDto createCategory(CreateCategoryForm createCategoryForm);

    void updateCategory(UpdateCategoryForm updateCategoryForm);

    void deleteCategory(Long id);

    Boolean checkCategoy(Long id);
}
