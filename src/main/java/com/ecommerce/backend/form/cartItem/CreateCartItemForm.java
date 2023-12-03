package com.ecommerce.backend.form.cartItem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCartItemForm {
    @NotNull(message = "quantity can not null")
    @ApiModelProperty(name = "quantity", required = true)
    private Integer quantity;

    @NotNull(message = "productVariationId can not null")
    @ApiModelProperty(name = "productVariationId", required = true)
    private Long productVariationId;
}
