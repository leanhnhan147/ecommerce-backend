package com.ecommerce.backend.dto.product;

import com.ecommerce.backend.dto.category.CategoryDto;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String avatar;
    private String description;
    private CategoryDto category;
}
