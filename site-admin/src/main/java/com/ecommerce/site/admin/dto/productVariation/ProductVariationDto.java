package com.ecommerce.site.admin.dto.productVariation;

import com.ecommerce.site.admin.dto.product.ProductDto;
import com.ecommerce.site.admin.dto.productVariationOptionValue.ProductVariationOptionValueDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductVariationDto {
    private Long id;
    private Double price;
    private Integer state;
    private ProductDto product;
    private List<ProductVariationOptionValueDto> productVariationOptionValues;
}
