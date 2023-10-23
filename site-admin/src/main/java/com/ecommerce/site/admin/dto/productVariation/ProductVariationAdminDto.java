package com.ecommerce.site.admin.dto.productVariation;

import com.ecommerce.site.admin.dto.BasicAdminDto;
import com.ecommerce.site.admin.dto.product.ProductDto;
import com.ecommerce.site.admin.dto.productVariationOptionValue.ProductVariationOptionValueDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductVariationAdminDto extends BasicAdminDto {
    private Double price;
    private Integer state;
    private ProductDto product;
    private List<ProductVariationOptionValueDto> productVariationOptionValues;
}
