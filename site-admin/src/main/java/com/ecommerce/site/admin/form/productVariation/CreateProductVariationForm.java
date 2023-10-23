package com.ecommerce.site.admin.form.productVariation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateProductVariationForm {
    @NotNull(message = "price can not be null")
    @ApiModelProperty(name = "price", required = true)
    private Integer price;

    @NotNull(message = "state can not be null")
    @ApiModelProperty(name = "state", required = true)
    private Integer state;

    @NotNull(message = "productId can not be null")
    @ApiModelProperty(name = "productId", required = true)
    private Long productId;

    @NotNull(message = "optionValues can not be null")
    @ApiModelProperty(name = "optionValues", required = true)
    private Long[] optionValues;
}
