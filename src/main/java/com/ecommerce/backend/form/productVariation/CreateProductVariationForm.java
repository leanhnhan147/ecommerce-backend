package com.ecommerce.backend.form.productVariation;

import com.ecommerce.backend.form.optionValueImage.CreateOptionValueImageForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateProductVariationForm {
    @NotNull(message = "state can not be null")
    @ApiModelProperty(name = "state", required = true)
    private Integer state;

    @NotNull(message = "productId can not be null")
    @ApiModelProperty(name = "productId", required = true)
    private Long productId;

    @NotNull(message = "optionVariationForms can not be null")
    @ApiModelProperty(name = "optionVariationForms", required = true)
    private Long[][] optionValues;

    private CreateOptionValueImageForm[] imageIds;
}
