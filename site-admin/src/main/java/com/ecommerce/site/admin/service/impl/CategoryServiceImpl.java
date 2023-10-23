package com.ecommerce.site.admin.service.impl;

import com.ecommerce.site.admin.dto.ResponseListDto;
import com.ecommerce.site.admin.dto.category.CategoryAdminDto;
import com.ecommerce.site.admin.dto.category.CategoryDto;
import com.ecommerce.site.admin.exception.NotFoundException;
import com.ecommerce.site.admin.form.category.CreateCategoryForm;
import com.ecommerce.site.admin.form.category.UpdateCategoryForm;
import com.ecommerce.site.admin.mapper.CategoryMapper;
import com.ecommerce.site.admin.repository.CategoryRepository;
import com.ecommerce.site.admin.service.CategoryService;
import com.ecommerce.site.admin.storage.criteria.CategoryCriteria;
import com.ecommerce.site.admin.storage.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public CategoryAdminDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found category"));
        return categoryMapper.fromEntityToCategoryAdminDto(category);
    }

    @Override
    public ResponseListDto<List<CategoryAdminDto>> getCategoryList(CategoryCriteria categoryCriteria, Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(categoryCriteria.getCriteria(), pageable);
        ResponseListDto<List<CategoryAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(categoryMapper.fromEntityListToCategoryAdminDtoList(categories.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(categories.getTotalPages());
        responseListDto.setTotalElements(categories.getTotalElements());
        return responseListDto;
    }

    @Override
    public void createCategory(CreateCategoryForm createCategoryForm) {
        Category category = categoryMapper.fromCreateCategoryFormToEntity(createCategoryForm);
        if(createCategoryForm.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(createCategoryForm.getParentId()).orElse(null);
            if(parentCategory == null ) {
                throw new NotFoundException("Not found parent category");
            }
            category.setParent(parentCategory);
            if (!parentCategory.getHasChildren()){
                parentCategory.setHasChildren(true);
            }
        }
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(UpdateCategoryForm updateCategoryForm) {
        Category category = categoryRepository.findById(updateCategoryForm.getId()).orElse(null);
        if(category == null){
            throw new NotFoundException("Not found category");
        }
        categoryMapper.fromUpdateCategoryFormToEntity(updateCategoryForm, category);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryDto> getCategoryListAutoComplete(CategoryCriteria categoryCriteria) {
        List<Category> categorys = categoryRepository.findAll(categoryCriteria.getCriteria());
        return categoryMapper.fromEntityListToCategoryDtoAutoCompleteList(categorys);
    }
}
