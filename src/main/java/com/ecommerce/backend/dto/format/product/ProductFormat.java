package com.ecommerce.backend.dto.format.product;

import com.ecommerce.backend.dto.format.option.OptionFormat;
import com.ecommerce.backend.dto.format.optionValue.OptionValueFormat;
import com.ecommerce.backend.dto.format.productImage.ProductImageFormat;
import com.ecommerce.backend.dto.format.productVariation.ProductVariationFormat;
import lombok.Data;

import java.util.List;

@Data
public class ProductFormat {
    private Long id;
    private String name;
    private Double avgRating;
    private Integer ratingCount;
    private Integer soldCount;
    private Integer stock;
    private List<OptionFormat> options;
    private List<List<OptionValueFormat>> optionValueGroups;
    private List<ProductVariationFormat> variations;
    private List<ProductImageFormat> images;
    private List<String> categories;
    private String description;
}