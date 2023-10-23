package com.ecommerce.site.admin.form.option;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateOptionForm {
    @NotEmpty(message = "name can not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotEmpty(message = "displayName can not be null")
    @ApiModelProperty(name = "displayName", required = true)
    private String displayName;

    @NotNull(message = "categoryId can not null")
    @ApiModelProperty(name = "categoryId", required = true)
    private Long categoryId;
}
