package com.ecommerce.backend.form.inventory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateInventoryForm {
    @NotNull(message = "originalPrice can not null")
    @ApiModelProperty(name = "originalPrice", required = true)
    private Double[] originalPrice;

    @NotNull(message = "quantity can not null")
    @ApiModelProperty(name = "quantity", required = true)
    private Integer[] quantity;

    @NotEmpty(message = "sku can not be null")
    @ApiModelProperty(name = "sku", required = true)
    private String[] sku;

    @NotNull(message = "productVariationId can not null")
    @ApiModelProperty(name = "productVariationId", required = true)
    private Long[] productVariationId;

    @NotNull(message = "userId can not null")
    @ApiModelProperty(name = "userId", required = true)
    private Long userId;
}
