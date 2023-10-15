package com.ecommerce.backend.dto.option;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.category.CategoryDto;
import lombok.Data;

@Data
public class OptionAdminDto extends BasicAdminDto {
    private String name;
    private String displayName;
    private CategoryDto category;
}
