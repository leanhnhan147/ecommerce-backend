package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.category.CategoryAdminDto;
import com.ecommerce.backend.dto.category.CategoryDto;
import com.ecommerce.backend.form.category.CreateCategoryForm;
import com.ecommerce.backend.form.category.UpdateCategoryForm;
import com.ecommerce.backend.storage.entity.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "level", target = "level")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    Category fromCreateCategoryFormToEntity(CreateCategoryForm createCategoryForm);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateCategoryFormToEntity(UpdateCategoryForm updateCategoryForm, @MappingTarget Category category);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "level", target = "level")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "hasChildren", target = "hasChildren")
    @Mapping(source = "parent", target = "parent")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    CategoryAdminDto fromEntityToCategoryAdminDto(Category category);

    @IterableMapping(elementTargetType = CategoryAdminDto.class, qualifiedByName = "adminGetMapping")
    List<CategoryAdminDto> fromEntityListToCategoryAdminDtoList(List<Category> categories);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "level", target = "level")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "hasChildren", target = "hasChildren")
    @Mapping(source = "parent", target = "parent")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCategoryDto")
    CategoryDto fromEntityToCategoryDto(Category category);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "level", target = "level")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "hasChildren", target = "hasChildren")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCategoryDtoAutoComplete")
    CategoryDto fromEntityToCategoryDtoAutoComplete(Category category);

    @IterableMapping(elementTargetType = CategoryDto.class, qualifiedByName = "fromEntityToCategoryDtoAutoComplete")
    List<CategoryDto> fromEntityListToCategoryDtoAutoCompleteList(List<Category> categories);
}

