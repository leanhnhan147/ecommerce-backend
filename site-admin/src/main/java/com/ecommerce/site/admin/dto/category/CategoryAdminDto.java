package com.ecommerce.site.admin.dto.category;

import com.ecommerce.site.admin.dto.BasicAdminDto;
import lombok.Data;

@Data
public class CategoryAdminDto extends BasicAdminDto {
    private String name;
    private Integer level;
    private Boolean hasChildren;
    private CategoryDto parent;
}
