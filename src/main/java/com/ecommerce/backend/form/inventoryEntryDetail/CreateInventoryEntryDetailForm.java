package com.ecommerce.backend.form.inventoryEntryDetail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateInventoryEntryDetailForm {
    @NotNull(message = "originalPrice can not null")
    @ApiModelProperty(name = "originalPrice", required = true)
    private Double[] originalPrice;

    @NotNull(message = "quantity can not null")
    @ApiModelProperty(name = "quantity", required = true)
    private Integer[] quantity;

    @NotNull(message = "productVariationId can not null")
    @ApiModelProperty(name = "productVariationId", required = true)
    private Long[] productVariationId;

    @NotNull(message = "inventoryEntryId can not null")
    @ApiModelProperty(name = "inventoryEntryId", required = true)
    private Long[] inventoryEntryId;
}
