package com.ecommerce.backend.dto.option;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.category.CategoryDto;
import lombok.Data;

import java.util.List;

@Data
public class OptionAdminDto extends BasicAdminDto {
    private String displayName;
    private String code;
    private List<CategoryDto> categories;
}
