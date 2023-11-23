package com.ecommerce.backend.form.inventoryEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateInventoryEntryForm {
    @NotNull(message = "totalPrice can not null")
    @ApiModelProperty(name = "totalPrice", required = true)
    private Double totalPrice;

    @NotEmpty(message = "invoiceCode can not be null")
    @ApiModelProperty(name = "invoiceCode", required = true)
    private String invoiceCode;

    @NotEmpty(message = "description can not be null")
    @ApiModelProperty(name = "description", required = true)
    private String description;

    @NotNull(message = "providerId can not null")
    @ApiModelProperty(name = "providerId", required = true)
    private Long providerId;

    @NotNull(message = "userId can not null")
    @ApiModelProperty(name = "userId", required = true)
    private Long userId;
}
