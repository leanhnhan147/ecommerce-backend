package com.ecommerce.backend.form.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
public class UpdateUserForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

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

    @ApiModelProperty(name = "password")
    private String password;
}
