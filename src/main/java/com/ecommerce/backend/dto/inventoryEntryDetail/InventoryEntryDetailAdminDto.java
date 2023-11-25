package com.ecommerce.backend.dto.inventoryEntryDetail;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
import lombok.Data;

@Data
public class InventoryEntryDetailAdminDto extends BasicAdminDto {
    private Double originalPrice;
    private Integer quantity;
    private ProductVariationDto productVariation;
    private InventoryEntryDto inventoryEntry;
}
