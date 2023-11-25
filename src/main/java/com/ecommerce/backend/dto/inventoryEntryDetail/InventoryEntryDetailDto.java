package com.ecommerce.backend.dto.inventoryEntryDetail;

import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
import lombok.Data;

@Data
public class InventoryEntryDetailDto {
    private Long id;
    private Double originalPrice;
    private Integer quantity;
    private ProductVariationDto productVariation;
    private InventoryEntryDto inventoryEntry;
}
