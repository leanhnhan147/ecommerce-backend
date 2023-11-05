package com.ecommerce.backend.dto.productImage;

import com.ecommerce.backend.dto.mediaResource.MediaResourceDto;
import com.ecommerce.backend.dto.optionValueImage.OptionValueImageDto;
import lombok.Data;

@Data
public class ProductImageDto {
    private MediaResourceDto mediaResource;
    private OptionValueImageDto optionValueImage;
}