package com.ecommerce.backend.form.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateStateOrderForm {
    @NotNull(message = "id can not null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotEmpty(message = "title can not be null")
    @ApiModelProperty(name = "title", required = true)
    private String title;

    @ApiModelProperty(name = "name")
    private String note;

    @NotNull(message = "state can not null")
    @ApiModelProperty(name = "state", required = true)
    private Integer state;
}
