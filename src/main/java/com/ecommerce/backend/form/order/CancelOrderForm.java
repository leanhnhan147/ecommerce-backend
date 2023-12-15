package com.ecommerce.backend.form.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CancelOrderForm {
    @NotNull(message = "id can not null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @ApiModelProperty(name = "name")
    private String note;
}
