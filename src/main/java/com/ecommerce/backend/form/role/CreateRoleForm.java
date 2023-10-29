package com.ecommerce.backend.form.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateRoleForm {
    @NotEmpty(message = "name can not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotEmpty(message = "description can not be null")
    @ApiModelProperty(name = "description", required = true)
    private String description;

    @NotNull(message = "permissionId can not null")
    @ApiModelProperty(name = "permissionId", required = true)
    private Long[] permissionId;
}
