package com.ecommerce.backend.dto.provider;

import com.ecommerce.backend.dto.BasicAdminDto;
import lombok.Data;

@Data
public class ProviderAdminDto extends BasicAdminDto {
    private String name;
    private String phone;
    private String address;
}
