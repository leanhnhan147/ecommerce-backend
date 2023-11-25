package com.ecommerce.backend.form.inventoryEntryDetail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateInventoryEntryDetailForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long[] id;

    @NotNull(message = "originalPrice can not null")
    @ApiModelProperty(name = "originalPrice", required = true)
    private Double[] originalPrice;

    @NotNull(message = "quantity can not null")
    @ApiModelProperty(name = "quantity", required = true)
    private Integer[] quantity;
}
