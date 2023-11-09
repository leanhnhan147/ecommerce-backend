package com.ecommerce.backend.dto.category;

import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private Integer level;
    private Boolean hasChildren;
    private String code;
    private CategoryDto parent;
}
