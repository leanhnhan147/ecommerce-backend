package com.ecommerce.site.admin.controller;

import com.ecommerce.site.admin.dto.ApiMessageDto;
import com.ecommerce.site.admin.dto.ResponseListDto;
import com.ecommerce.site.admin.dto.product.ProductAdminDto;
import com.ecommerce.site.admin.dto.product.ProductDto;
import com.ecommerce.site.admin.form.product.CreateProductForm;
import com.ecommerce.site.admin.form.product.UpdateProductForm;
import com.ecommerce.site.admin.service.ProductService;
import com.ecommerce.site.admin.storage.criteria.ProductCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProductDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<ProductDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(productService.getProductById(id));
        apiMessageDto.setMessage("Get product success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<ProductAdminDto>>> getList(ProductCriteria productCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<ProductAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(productService.getProductList(productCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list product success");
        return responseListDtoApiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateProductForm createProductForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        productService.createProduct(createProductForm);
        apiMessageDto.setMessage("Create product success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateProductForm updateProductForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        productService.updateProduct(updateProductForm);
        apiMessageDto.setMessage("Update product success");
        return apiMessageDto;
    }
}
