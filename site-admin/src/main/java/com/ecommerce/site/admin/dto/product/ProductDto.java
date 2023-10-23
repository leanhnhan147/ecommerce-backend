package com.ecommerce.site.admin.dto.product;

import com.ecommerce.site.admin.dto.category.CategoryDto;
import com.ecommerce.site.admin.dto.productVariation.ProductVariationDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String avatar;
    private String description;
    private Integer stock;
    private CategoryDto category;
    private List<ProductVariationDto> productVariations;
}
