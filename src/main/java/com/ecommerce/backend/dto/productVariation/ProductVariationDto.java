package com.ecommerce.backend.dto.productVariation;

import com.ecommerce.backend.dto.product.ProductDto;
import lombok.Data;

@Data
public class ProductVariationDto {
    private Long id;
    private Double originPrice;
    private Integer state;
    private ProductDto product;
}
