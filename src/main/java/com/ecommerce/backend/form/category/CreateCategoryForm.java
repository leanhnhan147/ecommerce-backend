package com.ecommerce.backend.form.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateCategoryForm {
    @NotEmpty(message = "name can not be null")
    @ApiModelProperty(required = true)
    private String name;

    @NotNull(message = "level can not be null")
    @ApiModelProperty(required = true)
    private Integer level;

    @ApiModelProperty(name = "parentId")
    private Long parentId;

    @NotNull(message = "status can not be null")
    @ApiModelProperty(required = true)
    private Integer status;
}
