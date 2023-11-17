package com.ecommerce.backend.dto.format.productVariation;

import lombok.Data;

import java.util.List;

@Data
public class ProductVariationFormat {
    private Double price;
    private Integer stock;
    private List<String> optionValues;
}