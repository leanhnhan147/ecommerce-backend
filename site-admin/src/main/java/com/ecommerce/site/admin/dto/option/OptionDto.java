package com.ecommerce.site.admin.dto.option;

import com.ecommerce.site.admin.dto.category.CategoryDto;
import lombok.Data;

@Data
public class OptionDto {
    private Long id;
    private String name;
    private String displayName;
    private CategoryDto category;
}
