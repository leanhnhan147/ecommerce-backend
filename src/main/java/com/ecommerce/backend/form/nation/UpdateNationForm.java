package com.ecommerce.backend.form.nation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateNationForm {
    @NotNull(message = "id cant not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotEmpty(message = "name cant not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotEmpty(message = "postCode cant not be null")
    @ApiModelProperty(name = "postCode", required = true)
    private String postCode;

    @ApiModelProperty(name = "parentId")
    private Long parentId;
}
