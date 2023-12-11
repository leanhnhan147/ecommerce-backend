package com.ecommerce.backend.dto.product;

import com.ecommerce.backend.dto.category.CategoryDto;
import com.ecommerce.backend.dto.productImage.ProductImageDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String avatar;
    private String description;
    private CategoryDto category;
    private List<ProductVariationDto> productVariations;
    private List<ProductImageDto> productImages;
}
