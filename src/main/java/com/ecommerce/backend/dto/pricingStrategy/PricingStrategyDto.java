package com.ecommerce.backend.dto.pricingStrategy;

import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
import lombok.Data;

import java.util.Date;

@Data
public class PricingStrategyDto {
    private Long id;
    private Double originalPrice;
    private Double price;
    private Double discountedPrice;
    private Date startDate;
    private Date endDate;
    private Integer state;
    private ProductVariationDto productVariation;
}
