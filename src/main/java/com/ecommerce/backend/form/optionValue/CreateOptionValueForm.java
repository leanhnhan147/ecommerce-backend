package com.ecommerce.backend.form.optionValue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateOptionValueForm {
    @NotEmpty(message = "value can not be null")
    @ApiModelProperty(name = "value", required = true)
    private String value;

    @NotEmpty(message = "displayName can not be null")
    @ApiModelProperty(name = "displayName", required = true)
    private String displayName;

    @NotNull(message = "optionId can not null")
    @ApiModelProperty(name = "optionId", required = true)
    private Long optionId;
}
