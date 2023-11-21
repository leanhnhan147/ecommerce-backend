package com.ecommerce.backend.dto.nation;

import com.ecommerce.backend.dto.BasicAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NationAdminDto extends BasicAdminDto {
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "postCode")
    private String postCode;
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "parent")
    private NationDto parent;
}
