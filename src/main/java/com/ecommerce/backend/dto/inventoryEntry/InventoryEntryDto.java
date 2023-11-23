package com.ecommerce.backend.dto.inventoryEntry;

import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
import lombok.Data;

import java.util.Date;

@Data
public class InventoryEntryDto {
    private Long id;
    private Double totalPrice;
    private String invoiceCode;
    private String description;
}
