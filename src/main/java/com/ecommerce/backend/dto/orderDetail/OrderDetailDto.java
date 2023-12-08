package com.ecommerce.backend.dto.orderDetail;

import com.ecommerce.backend.dto.productVariation.ProductVariationCartItemDto;
import lombok.Data;

@Data
public class OrderDetailDto {
    private Long id;
    private Integer quantity;
    private Double totalPrice;
    private ProductVariationCartItemDto productVariation;
}
