package com.ecommerce.site.admin.controller;

import com.ecommerce.site.admin.dto.ApiMessageDto;
import com.ecommerce.site.admin.dto.ResponseListDto;
import com.ecommerce.site.admin.dto.category.CategoryAdminDto;
import com.ecommerce.site.admin.dto.category.CategoryDto;
import com.ecommerce.site.admin.form.category.CreateCategoryForm;
import com.ecommerce.site.admin.form.category.UpdateCategoryForm;
import com.ecommerce.site.admin.service.CategoryService;
import com.ecommerce.site.admin.storage.criteria.CategoryCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CategoryAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<CategoryAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(categoryService.getCategoryById(id));
        apiMessageDto.setMessage("Get category success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<CategoryAdminDto>>> getList(CategoryCriteria categoryCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<CategoryAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(categoryService.getCategoryList(categoryCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list category success");
        return responseListDtoApiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateCategoryForm createCategoryForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        categoryService.createCategory(createCategoryForm);
        apiMessageDto.setMessage("Create category success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateCategoryForm updateCategoryForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        categoryService.updateCategory(updateCategoryForm);
        apiMessageDto.setMessage("Update category success");
        return apiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<CategoryDto>> getListAutoComplete(CategoryCriteria categoryCriteria) {
        ApiMessageDto<List<CategoryDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(categoryService.getCategoryListAutoComplete(categoryCriteria));
        apiMessageDto.setMessage("Get list auto complete success");
        return apiMessageDto;
    }
}
