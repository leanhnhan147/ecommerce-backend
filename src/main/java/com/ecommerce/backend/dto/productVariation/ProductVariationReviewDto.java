package com.ecommerce.backend.dto.productVariation;

import com.ecommerce.backend.dto.product.ProductDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductVariationReviewDto {
    private Long id;
    private String name;
    private List<String> optionValues;
    private ProductDto product;
}
