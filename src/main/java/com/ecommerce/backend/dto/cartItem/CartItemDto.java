package com.ecommerce.backend.dto.cartItem;

import com.ecommerce.backend.dto.productVariation.ProductVariationCartItemDto;
import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Integer quantity;
    private ProductVariationCartItemDto productVariation;
}
