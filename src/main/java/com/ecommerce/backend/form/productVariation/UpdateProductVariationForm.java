package com.ecommerce.backend.form.productVariation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateProductVariationForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotNull(message = "originPrice can not be null")
    @ApiModelProperty(name = "originPrice", required = true)
    private Integer originPrice;

    @NotNull(message = "state can not be null")
    @ApiModelProperty(name = "state", required = true)
    private Integer state;
}
