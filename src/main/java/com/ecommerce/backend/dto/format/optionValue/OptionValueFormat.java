package com.ecommerce.backend.dto.format.optionValue;

import lombok.Data;

@Data
public class OptionValueFormat {
    private Long id;
    private String displayName;
    private Long imageId;
    private Long optionId;
}