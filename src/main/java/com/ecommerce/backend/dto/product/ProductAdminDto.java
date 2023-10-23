package com.ecommerce.backend.dto.product;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.category.CategoryDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
import com.ecommerce.backend.storage.entity.ProductVariation;
import lombok.Data;

import java.util.List;

@Data
public class ProductAdminDto extends BasicAdminDto {
    private String name;
    private String avatar;
    private String description;
    private Integer stock;
    private CategoryDto category;
    private List<ProductVariationDto> productVariations;
}
