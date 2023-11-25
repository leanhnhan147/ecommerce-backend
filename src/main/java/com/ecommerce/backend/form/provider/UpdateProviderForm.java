package com.ecommerce.backend.form.provider;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateProviderForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotEmpty(message = "name can not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotEmpty(message = "phone can not be null")
    @ApiModelProperty(name = "phone", required = true)
    private String phone;

    @NotEmpty(message = "address can not be null")
    @ApiModelProperty(name = "address", required = true)
    private String address;
}
