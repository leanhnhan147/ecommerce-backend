package com.ecommerce.backend.form.option;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateOptionForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotEmpty(message = "name can not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotEmpty(message = "displayName can not be null")
    @ApiModelProperty(name = "displayName", required = true)
    private String displayName;
}
