package com.ecommerce.backend.form.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
public class UpdateCustomerForm {
    @NotNull(message = "id cannot be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @ApiModelProperty(name = "avatar")
    private String avatar;

    @NotNull(message = "gender cannot be null")
    @ApiModelProperty(name = "gender", required = true)
    private Integer gender;

    @ApiModelProperty(name = "birthday", required = true)
    @Past(message = "date of birth must be in the past")
    private Date birhday;

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

    @ApiModelProperty(name = "password")
    private String password;
}
