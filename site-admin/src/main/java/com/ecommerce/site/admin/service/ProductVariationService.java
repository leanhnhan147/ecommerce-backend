package com.ecommerce.site.admin.service;

import com.ecommerce.site.admin.dto.ResponseListDto;
import com.ecommerce.site.admin.dto.productVariation.ProductVariationAdminDto;
import com.ecommerce.site.admin.form.productVariation.CreateProductVariationForm;
import com.ecommerce.site.admin.form.productVariation.UpdateProductVariationForm;
import com.ecommerce.site.admin.storage.criteria.ProductVariationCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductVariationService {

    ProductVariationAdminDto getProductVariationById(Long id);

    ResponseListDto<List<ProductVariationAdminDto>> getProductVariationList(ProductVariationCriteria productVariationCriteria, Pageable pageable);

    void createProductVariation(CreateProductVariationForm createProductVariationForm);

    void updateProductVariation(UpdateProductVariationForm updateProductVariationForm);
}
