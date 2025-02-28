package com.ecommerce.backend.dto.category;

import com.ecommerce.backend.dto.BasicAdminDto;
import lombok.Data;

@Data
public class CategoryAdminDto extends BasicAdminDto {
    private String name;
    private Integer level;
    private Boolean hasChildren;
    private String code;
    private CategoryDto parent;
}
