package com.ecommerce.site.admin.dto.category;

import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private Integer level;
    private Boolean hasChildren;
    private CategoryDto parent;
}
