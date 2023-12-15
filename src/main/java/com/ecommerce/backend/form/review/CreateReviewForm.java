package com.ecommerce.backend.form.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateReviewForm {
    @NotEmpty(message = "content can not be null")
    @ApiModelProperty(name = "content", required = true)
    private String content;

    @NotNull(message = "point can not null")
    @ApiModelProperty(name = "point", required = true)
    private Integer point;

    @NotNull(message = "imageIds can not null")
    @ApiModelProperty(name = "imageIds", required = true)
    private Long[] imageIds;

    @NotNull(message = "productVariationId can not null")
    @ApiModelProperty(name = "productVariationId", required = true)
    private Long productVariationId;

    @NotNull(message = "orderId can not null")
    @ApiModelProperty(name = "orderId", required = true)
    private Long orderId;
}
