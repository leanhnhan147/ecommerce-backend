package com.ecommerce.backend.form.nation;

import com.ecommerce.backend.validation.NationKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateNationForm {
    @NotEmpty(message = "name cant not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotEmpty(message = "postCode cant not be null")
    @ApiModelProperty(name = "postCode", required = true)
    private String postCode;

    @NationKind
    @NotNull(message = "kind cant not be null")
    @ApiModelProperty(name = "kind", required = true)
    private Integer kind;

    @ApiModelProperty(name = "parentId")
    private Long parentId;
}
