package com.ecommerce.backend.form.optionValueImage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateOptionValueImageForm {
    @NotNull(message = "optionValueId can not null")
    @ApiModelProperty(name = "optionValueId", required = true)
    private Long optionValueId;

    @NotNull(message = "imageId can not null")
    @ApiModelProperty(name = "imageId", required = true)
    private Long imageId;
}
