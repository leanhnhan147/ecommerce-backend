package com.ecommerce.backend.dto.inventory;

import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
import lombok.Data;

import java.util.Date;

@Data
public class InventoryDto {
    private Long id;
    private Double originalPrice;
    private Integer quantity;
    private String sku;
    private Date importTime;
    private ProductVariationDto productVariation;
}
