package com.ecommerce.backend.form.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateStateReviewForm {
    @NotNull(message = "id can not null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotNull(message = "state can not null")
    @ApiModelProperty(name = "state", required = true)
    private Integer state;
}
