package com.ecommerce.backend.dto.productVariation;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.product.ProductDto;
import lombok.Data;

@Data
public class ProductVariationAdminDto extends BasicAdminDto {
    private Double originPrice;
    private Integer state;
    private ProductDto product;
}
