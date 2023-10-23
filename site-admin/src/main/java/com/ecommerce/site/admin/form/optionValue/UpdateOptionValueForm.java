package com.ecommerce.site.admin.form.optionValue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateOptionValueForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotEmpty(message = "value can not be null")
    @ApiModelProperty(name = "value", required = true)
    private String value;

    @NotEmpty(message = "displayName can not be null")
    @ApiModelProperty(name = "displayName", required = true)
    private String displayName;
}
