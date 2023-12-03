package com.ecommerce.backend.form.cartItem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCartItemForm {
    @NotNull(message = "cartItemId can not null")
    @ApiModelProperty(name = "cartItemId", required = true)
    private Long cartItemId;

    @NotNull(message = "quantity can not null")
    @ApiModelProperty(name = "quantity", required = true)
    private Integer quantity;
}
