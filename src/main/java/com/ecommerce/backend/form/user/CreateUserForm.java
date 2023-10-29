package com.ecommerce.backend.form.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class CreateUserForm {
    @NotEmpty(message = "fullName can not be null")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;

    @ApiModelProperty(name = "avatar")
    private String avatar;

    @ApiModelProperty(name = "birthday", required = true)
    @Past(message = "date of birth must be in the past")
    private Date birhday;

    @NotEmpty(message = "phone can not be null")
    @ApiModelProperty(name = "phone", required = true)
    private String phone;

    @NotEmpty(message = "email can not be null")
    @ApiModelProperty(name = "email", required = true)
    @Email
    private String email;

    @NotEmpty(message = "username can not be null")
    @ApiModelProperty(name = "username", required = true)
    private String username;

    @NotEmpty(message = "password minimum 6 characters")
    @ApiModelProperty(name = "password", required = true)
    @Size(min = 6)
    private String password;

    @NotNull(message = "roleId can not null")
    @ApiModelProperty(name = "roleId", required = true)
    private Long roleId;
}
