package com.ecommerce.backend.dto.inventory;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
import com.ecommerce.backend.dto.user.UserDto;
import lombok.Data;

import java.util.Date;

@Data
public class InventoryAdminDto extends BasicAdminDto {
    private Double originalPrice;
    private String quantity;
    private String sku;
    private Date importTime;
    private ProductVariationDto productVariation;
    private UserDto user;
}
