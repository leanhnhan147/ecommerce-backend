package com.ecommerce.backend.form.address;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateAddressForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotEmpty(message = "receiverName cant not be null")
    @ApiModelProperty(name = "receiverName", required = true)
    private String receiverName;

    @NotEmpty(message = "receiverPhone cant not be null")
    @ApiModelProperty(name = "receiverPhone", required = true)
    private String receiverPhone;

    @NotEmpty(message = "addressDetail cant not be null")
    @ApiModelProperty(name = "addressDetail", required = true)
    private String addressDetail;

    @NotNull(message = "wardId can not null")
    @ApiModelProperty(name = "wardId", required = true)
    private Long wardId;

    @NotNull(message = "districtId can not null")
    @ApiModelProperty(name = "districtId", required = true)
    private Long districtId;

    @NotNull(message = "provinceId can not null")
    @ApiModelProperty(name = "provinceId", required = true)
    private Long provinceId;
}
