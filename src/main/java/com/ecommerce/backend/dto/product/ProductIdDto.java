package com.ecommerce.backend.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductIdDto {
    private Long productId;
    private List<Long> imageIds;
}
