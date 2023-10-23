package com.ecommerce.site.admin.controller;


import com.ecommerce.site.admin.dto.ApiMessageDto;
import com.ecommerce.site.admin.dto.ResponseListDto;
import com.ecommerce.site.admin.dto.productVariation.ProductVariationAdminDto;
import com.ecommerce.site.admin.form.productVariation.CreateProductVariationForm;
import com.ecommerce.site.admin.form.productVariation.UpdateProductVariationForm;
import com.ecommerce.site.admin.service.ProductVariationService;
import com.ecommerce.site.admin.storage.criteria.ProductVariationCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/product-variation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ProductVariationController {

    @Autowired
    ProductVariationService productVariationService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProductVariationAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<ProductVariationAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(productVariationService.getProductVariationById(id));
        apiMessageDto.setMessage("Get product variation success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<ProductVariationAdminDto>>> getList(ProductVariationCriteria productVariationCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<ProductVariationAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(productVariationService.getProductVariationList(productVariationCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list product variation success");
        return responseListDtoApiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateProductVariationForm createProductVariationForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        productVariationService.createProductVariation(createProductVariationForm);
        apiMessageDto.setMessage("Create product variation success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateProductVariationForm updateProductVariationForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        productVariationService.updateProductVariation(updateProductVariationForm);
        apiMessageDto.setMessage("Update product variation success");
        return apiMessageDto;
    }
}
