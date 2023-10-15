package com.ecommerce.backend.dto.optionValue;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.option.OptionDto;
import lombok.Data;

@Data
public class OptionValueAdminDto extends BasicAdminDto {
    private String value;
    private String displayName;
    private OptionDto option;
}
