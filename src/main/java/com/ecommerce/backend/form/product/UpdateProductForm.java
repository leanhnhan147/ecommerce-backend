package com.ecommerce.backend.form.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateProductForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotEmpty(message = "name can not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotEmpty(message = "description can not be null")
    @ApiModelProperty(name = "description", required = true)
    private String description;

    @NotNull(message = "status can not be null")
    @ApiModelProperty(name = "status", required = true)
    private Integer status;

    @NotNull(message = "options can not be null")
    @ApiModelProperty(name = "options", required = true)
    private Long[] optionIds;

//    @NotNull(message = "images can not be null")
//    @ApiModelProperty(name = "images", required = true)
//    private MultipartFile[] images;
}
