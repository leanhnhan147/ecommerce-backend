package com.ecommerce.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BasicAdminDto {
    @ApiModelProperty(name = "id")
    private Long id;

    @ApiModelProperty(name = "status")
    private Integer status;

    @ApiModelProperty(name = "createdDate")
    private Date createdDate;

    @ApiModelProperty(name = "modifiedDate")
    private Date modifiedDate;
}
