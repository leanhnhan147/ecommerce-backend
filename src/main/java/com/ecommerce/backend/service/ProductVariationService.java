package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationAdminDto;
import com.ecommerce.backend.form.productVariation.CreateProductVariationForm;
import com.ecommerce.backend.form.productVariation.UpdateProductVariationForm;
import com.ecommerce.backend.storage.criteria.ProductVariationCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductVariationService {

    ProductVariationAdminDto getProductVariationById(Long id);

    ResponseListDto<List<ProductVariationAdminDto>> getProductVariationList(ProductVariationCriteria productVariationCriteria, Pageable pageable);

    void createProductVariation(CreateProductVariationForm createProductVariationForm);

    void updateProductVariation(UpdateProductVariationForm updateProductVariationForm);
}
