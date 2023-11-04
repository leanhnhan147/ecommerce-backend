package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.format.product.ProductFormat;
import com.ecommerce.backend.dto.product.ProductAdminDto;
import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.form.product.CreateProductForm;
import com.ecommerce.backend.form.product.UpdateProductForm;
import com.ecommerce.backend.storage.criteria.ProductCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductFormat getProductById(Long id);

    ResponseListDto<List<ProductAdminDto>> getProductList(ProductCriteria productCriteria, Pageable pageable);

    void createProduct(CreateProductForm createProductForm);

    void updateProduct(UpdateProductForm updateProductForm);
}
