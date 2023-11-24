package com.ecommerce.backend.dto.pricingStrategy;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
import com.ecommerce.backend.dto.user.UserDto;
import lombok.Data;

import java.util.Date;

@Data
public class PricingStrategyAdminDto extends BasicAdminDto {
    private Double price;
    private Double discountedPrice;
    private String sku;
    private Date startDate;
    private Date endDate;
    private Integer state;
    private ProductVariationDto productVariation;
    private InventoryEntryDto inventoryEntry;
    private UserDto user;
}
