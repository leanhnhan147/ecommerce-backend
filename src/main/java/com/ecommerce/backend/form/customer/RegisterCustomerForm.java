package com.ecommerce.backend.form.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class RegisterCustomerForm {
    @NotEmpty(message = "fullName cannot be null")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;

    @NotEmpty(message = "phone cannot be null")
    @ApiModelProperty(name = "phone", required = true)
    private String phone;

    @Email
    @NotEmpty(message = "email cannot be null")
    @ApiModelProperty(name = "email", required = true)
    private String email;

    @NotEmpty(message = "username cannot be null")
    @ApiModelProperty(name = "username", required = true)
    private String username;

    @NotEmpty(message = "password cannot be null")
    @ApiModelProperty(name = "password", required = true)
    private String password;
}
