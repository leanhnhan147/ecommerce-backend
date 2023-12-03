package com.ecommerce.backend.dto.address;

import com.ecommerce.backend.dto.nation.NationDto;
import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String receiverName;
    private String receiverPhone;
    private String addressDetail;
    private NationDto ward;
    private NationDto district;
    private NationDto province;
    private Integer isDefault;
}
