package com.ecommerce.backend.dto.optionValue;

import com.ecommerce.backend.dto.option.OptionDto;
import lombok.Data;

@Data
public class OptionValueDto {
    private Long id;
    private String displayName;
    private String code;
    private OptionDto option;
}
