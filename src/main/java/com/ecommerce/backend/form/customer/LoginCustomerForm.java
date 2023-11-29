package com.ecommerce.backend.form.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginCustomerForm {
    @NotEmpty(message = "username cannot be null")
    @ApiModelProperty(name = "username", required = true)
    private String username;
    @NotEmpty(message = "password cannot be null")
    @ApiModelProperty(name = "password", required = true)
    private String password;
}
