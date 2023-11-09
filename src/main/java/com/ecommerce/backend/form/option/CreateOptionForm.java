package com.ecommerce.backend.form.option;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateOptionForm {
    @NotEmpty(message = "displayName can not be null")
    @ApiModelProperty(name = "displayName", required = true)
    private String displayName;

    @NotEmpty(message = "code can not be null")
    @ApiModelProperty(name = "code", required = true)
    private String code;

    @NotNull(message = "categoryIds can not null")
    @ApiModelProperty(name = "categoryIds", required = true)
    private Long[] categoryIds;
}
