package com.ecommerce.backend.dto.nation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NationDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "postCode")
    private String postCode;
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "parent")
    private NationDto parent;
}
