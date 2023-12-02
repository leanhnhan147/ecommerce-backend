package com.ecommerce.backend.form.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
public class UpdateProfileUserForm {
    @NotEmpty(message = "fullName can not be null")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;

    @ApiModelProperty(name = "avatar")
    private String avatar;

    @ApiModelProperty(name = "birthday", required = true)
    @Past(message = "date of birth must be in the past")
    private Date birhday;

    @NotEmpty(message = "phone cannot be null")
    @ApiModelProperty(name = "phone", required = true)
    private String phone;

    @Email
    @NotEmpty(message = "email cannot be null")
    @ApiModelProperty(name = "email", required = true)
    private String email;

    @NotEmpty(message = "oldPassword cannot be null")
    @ApiModelProperty(required = true)
    private String oldPassword;

    @ApiModelProperty(name = "newPassword")
    private String newPassword;

    @ApiModelProperty(name = "confirmNewPassword")
    private String confirmNewPassword;
}
