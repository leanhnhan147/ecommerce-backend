package com.ecommerce.backend.dto.review;

import com.ecommerce.backend.dto.customer.CustomerDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationReviewDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReviewDto {
    private Long id;
    private String content;
    private Integer point;
    private Integer state;
    private Date createdDate;
    private CustomerDto customer;
    private ProductVariationReviewDto productVariation;
    private List<String> images;
}
