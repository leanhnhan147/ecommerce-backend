package com.ecommerce.backend.dto.category;

import com.ecommerce.backend.dto.BasicAdminDto;
import lombok.Data;

@Data
public class CategoryAdminDto extends BasicAdminDto {
    private String name;
    private Integer level;
    private CategoryDto parent;
}
