package com.ecommerce.backend.dto.provider;

import lombok.Data;

@Data
public class ProviderDto {
    private Long id;
    private String name;
    private String phone;
    private String address;
}
