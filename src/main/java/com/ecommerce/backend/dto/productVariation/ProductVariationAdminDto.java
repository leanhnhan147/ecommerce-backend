package com.ecommerce.backend.dto.productVariation;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.dto.productVariationOptionValue.ProductVariationOptionValueDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductVariationAdminDto extends BasicAdminDto {
    private String name;
    private Double price;
    private Double discountedPrice;
    private Integer stock;
    private Integer state;
    private ProductDto product;
    private List<ProductVariationOptionValueDto> productVariationOptionValues;
}
