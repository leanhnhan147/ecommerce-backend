package com.ecommerce.site.admin.dto.optionValue;

import com.ecommerce.site.admin.dto.BasicAdminDto;
import com.ecommerce.site.admin.dto.option.OptionDto;
import lombok.Data;

@Data
public class OptionValueAdminDto extends BasicAdminDto {
    private String value;
    private String displayName;
    private OptionDto option;
}
