package com.ecommerce.backend.dto.productVariation;

import com.ecommerce.backend.dto.product.ProductDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductVariationCartItemDto {
    private Long id;
    private String name;
    private Double price;
    private Double discountedPrice;
    private List<String> optionValues;
    private ProductDto product;
}
