package com.ecommerce.backend.form.productVariation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateProductVariationForm {
    @NotNull(message = "originPrice can not be null")
    @ApiModelProperty(name = "originPrice", required = true)
    private Integer originPrice;

    @NotNull(message = "state can not be null")
    @ApiModelProperty(name = "state", required = true)
    private Integer state;

    @NotNull(message = "productId can not null")
    @ApiModelProperty(name = "productId", required = true)
    private Long productId;
}
