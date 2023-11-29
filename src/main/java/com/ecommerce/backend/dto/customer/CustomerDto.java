package com.ecommerce.backend.dto.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "fullName")
    private String fullName;
    @ApiModelProperty(name = "avatar")
    private String avatar;
    @ApiModelProperty(name = "gender")
    private Integer gender;
    @ApiModelProperty(name = "birhday")
    private Date birhday;
    @ApiModelProperty(name = "phone")
    private String phone;
    @ApiModelProperty(name = "email")
    private String email;
    @ApiModelProperty(name = "username")
    private String username;
}
