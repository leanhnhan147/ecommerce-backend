package com.ecommerce.backend.dto.option;

import com.ecommerce.backend.dto.category.CategoryDto;
import lombok.Data;

@Data
public class OptionDto {
    private Long id;
    private String name;
    private String displayName;
    private CategoryDto category;
}
