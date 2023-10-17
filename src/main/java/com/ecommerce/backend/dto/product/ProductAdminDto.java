package com.ecommerce.backend.dto.product;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.category.CategoryDto;
import lombok.Data;

@Data
public class ProductAdminDto extends BasicAdminDto {
    private String name;
    private String avatar;
    private String description;
    private Integer stock;
    private CategoryDto category;
}
