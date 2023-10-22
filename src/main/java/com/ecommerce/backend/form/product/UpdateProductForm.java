package com.ecommerce.backend.form.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateProductForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotEmpty(message = "name can not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotEmpty(message = "avatar can not be null")
    @ApiModelProperty(name = "avatar", required = true)
    private String avatar;

    @NotEmpty(message = "description can not be null")
    @ApiModelProperty(name = "description", required = true)
    private String description;

    @NotNull(message = "status can not be null")
    @ApiModelProperty(name = "status", required = true)
    private Integer status;

    @NotNull(message = "options can not be null")
    @ApiModelProperty(name = "options", required = true)
    private Long[] options;
}
