package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.category.CategoryAdminDto;
import com.ecommerce.backend.dto.category.CategoryDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.exception.RequestException;
import com.ecommerce.backend.form.category.CreateCategoryForm;
import com.ecommerce.backend.form.category.UpdateCategoryForm;
import com.ecommerce.backend.mapper.CategoryMapper;
import com.ecommerce.backend.repository.CategoryRepository;
import com.ecommerce.backend.service.CategoryService;
import com.ecommerce.backend.storage.criteria.CategoryCriteria;
import com.ecommerce.backend.storage.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            category.setParent(parentCategory(createCategoryForm.getLevel(), createCategoryForm.getParentId()));
        }
        categoryRepository.save(category);
    }

    private Category parentCategory(Integer level, Long parentId){
        if(Objects.equals(level, Constant.CATEGORY_LEVEL_1)){
            throw new RequestException("Category level 1 cannot have a parent");
        }
        Category parentCategory = categoryRepository.findById(parentId).orElse(null);
        if(parentCategory == null ) {
            throw new NotFoundException("Not found parent category");
        }
        if(level.intValue() - parentCategory.getLevel().intValue() != 1){
            throw new RequestException("Parent category not valid");
        }
        if (!parentCategory.getHasChildren()){
            parentCategory.setHasChildren(true);
        }
        return parentCategory;
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
