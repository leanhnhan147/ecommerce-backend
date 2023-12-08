package com.ecommerce.backend.form.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateOrderForm {
    @NotNull(message = "paymentMethod can not null")
    @ApiModelProperty(name = "paymentMethod", required = true)
    private Integer paymentMethod;

    @NotNull(message = "cartItemIds can not null")
    @ApiModelProperty(name = "cartItemIds", required = true)
    private Long[] cartItemIds;

    @NotNull(message = "addressId can not null")
    @ApiModelProperty(name = "addressId", required = true)
    private Long addressId;
}
