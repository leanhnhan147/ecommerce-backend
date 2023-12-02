package com.ecommerce.backend.form.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
public class UpdateProfileCustomerForm {
    @NotEmpty(message = "fullName cannot be null")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;

    @ApiModelProperty(name = "avatar")
    private String avatar;

    @NotEmpty(message = "fullName cannot be null")
    @ApiModelProperty(name = "avatar")
    private String gender;

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
