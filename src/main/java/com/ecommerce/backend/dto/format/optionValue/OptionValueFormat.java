package com.ecommerce.backend.dto.format.optionValue;

import lombok.Data;

@Data
public class OptionValueFormat {
    private String code;
    private String displayName;
    private Long imageId;
    private String optionId;
}