package com.ecommerce.backend.form.groupPermission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateGroupPermissionForm {
    @NotEmpty(message = "name can not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotEmpty(message = "description can not be null")
    @ApiModelProperty(name = "description", required = true)
    private String description;
}
