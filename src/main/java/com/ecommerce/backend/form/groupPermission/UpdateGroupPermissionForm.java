package com.ecommerce.backend.form.groupPermission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateGroupPermissionForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotEmpty(message = "name can not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotEmpty(message = "description can not be null")
    @ApiModelProperty(name = "description", required = true)
    private String description;
}
