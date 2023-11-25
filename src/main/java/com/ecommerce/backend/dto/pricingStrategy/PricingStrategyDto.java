package com.ecommerce.backend.dto.pricingStrategy;

import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
import lombok.Data;

import java.util.Date;

@Data
public class PricingStrategyDto {
    private Long id;
    private Double price;
    private Double discountedPrice;
    private String sku;
    private Date startDate;
    private Date endDate;
    private Integer state;
    private ProductVariationDto productVariation;
    private InventoryEntryDto inventoryEntry;
}
