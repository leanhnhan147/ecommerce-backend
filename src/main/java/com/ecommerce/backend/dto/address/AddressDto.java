package com.ecommerce.backend.dto.address;

import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String receiverName;
    private String receiverPhone;
    private String addressDetail;
    private Integer isDefault;
}
