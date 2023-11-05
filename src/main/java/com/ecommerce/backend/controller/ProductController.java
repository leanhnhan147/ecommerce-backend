package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.format.product.ProductFormat;
import com.ecommerce.backend.dto.product.ProductAdminDto;
import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.form.product.CreateProductForm;
import com.ecommerce.backend.form.product.UpdateProductForm;
import com.ecommerce.backend.service.ProductService;
import com.ecommerce.backend.storage.criteria.ProductCriteria;
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
    public ApiMessageDto<ProductFormat> get(@PathVariable("id") Long id) {
        ApiMessageDto<ProductFormat> apiMessageDto = new ApiMessageDto<>();
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

    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ApiMessageDto<String> create(@Valid CreateProductForm createProductForm, BindingResult bindingResult) {
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
