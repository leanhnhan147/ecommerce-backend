package com.ecommerce.backend.dto.inventoryEntry;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
import com.ecommerce.backend.dto.provider.ProviderDto;
import com.ecommerce.backend.dto.user.UserDto;
import lombok.Data;

import java.util.Date;

@Data
public class InventoryEntryAdminDto extends BasicAdminDto {
    private Double totalPrice;
    private String invoiceCode;
    private String description;
    private ProviderDto provider;
    private UserDto user;
}
