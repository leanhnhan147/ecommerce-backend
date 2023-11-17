package com.ecommerce.backend.dto.format.optionValue;

import lombok.Data;

@Data
public class OptionValueFormat {
    private String id;
    private String displayName;
    private Long imageId;
    private String optionId;
}