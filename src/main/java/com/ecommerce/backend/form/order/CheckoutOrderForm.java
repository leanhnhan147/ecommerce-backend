package com.ecommerce.backend.form.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CheckoutOrderForm {
    @NotNull(message = "cartItemIds can not null")
    @ApiModelProperty(name = "cartItemIds", required = true)
    private Long[] cartItemIds;
}
