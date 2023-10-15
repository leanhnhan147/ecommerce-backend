package com.ecommerce.backend.form.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCategoryForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(required = true)
    private Long id;

    @NotEmpty(message = "name can not be null")
    @ApiModelProperty(required = true)
    private String name;

    @ApiModelProperty(required = true)
    private Integer status;
}
