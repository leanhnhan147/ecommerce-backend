package com.ecommerce.site.admin.service;

import com.ecommerce.site.admin.dto.ResponseListDto;
import com.ecommerce.site.admin.dto.category.CategoryAdminDto;
import com.ecommerce.site.admin.dto.category.CategoryDto;
import com.ecommerce.site.admin.form.category.CreateCategoryForm;
import com.ecommerce.site.admin.form.category.UpdateCategoryForm;
import com.ecommerce.site.admin.storage.criteria.CategoryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    CategoryAdminDto getCategoryById(Long id);

    ResponseListDto<List<CategoryAdminDto>> getCategoryList(CategoryCriteria categoryCriteria, Pageable pageable);

    void createCategory(CreateCategoryForm createCategoryForm);

    void updateCategory(UpdateCategoryForm updateCategoryForm);

    List<CategoryDto> getCategoryListAutoComplete(CategoryCriteria categoryCriteria);
}
