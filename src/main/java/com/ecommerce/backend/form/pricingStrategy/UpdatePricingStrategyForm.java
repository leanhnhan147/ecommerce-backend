package com.ecommerce.backend.form.pricingStrategy;

import com.ecommerce.backend.validation.PricingStrategyState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdatePricingStrategyForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long[] id;

    @NotNull(message = "price can not null")
    @ApiModelProperty(name = "price", required = true)
    private Double[] price;

    @NotNull(message = "discountedPrice can not null")
    @ApiModelProperty(name = "discountedPrice", required = true)
    private Double[] discountedPrice;

    @NotNull(message = "startDate can not null")
    @ApiModelProperty(name = "startDate", required = true)
    private Date[] startDate;

    @NotNull(message = "endDate can not null")
    @ApiModelProperty(name = "endDate", required = true)
    private Date[] endDate;

//    @PricingStrategyState
    @NotNull(message = "state can not null")
    @ApiModelProperty(name = "state", required = true)
    private Integer[] state;
}
