package com.ecommerce.site.admin.service;

import com.ecommerce.site.admin.dto.ResponseListDto;
import com.ecommerce.site.admin.dto.product.ProductAdminDto;
import com.ecommerce.site.admin.dto.product.ProductDto;
import com.ecommerce.site.admin.form.product.CreateProductForm;
import com.ecommerce.site.admin.form.product.UpdateProductForm;
import com.ecommerce.site.admin.storage.criteria.ProductCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductDto getProductById(Long id);

    ResponseListDto<List<ProductAdminDto>> getProductList(ProductCriteria productCriteria, Pageable pageable);

    void createProduct(CreateProductForm createProductForm);

    void updateProduct(UpdateProductForm updateProductForm);
}
