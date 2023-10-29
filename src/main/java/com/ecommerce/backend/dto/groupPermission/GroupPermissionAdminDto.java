package com.ecommerce.backend.dto.groupPermission;

import com.ecommerce.backend.dto.BasicAdminDto;
import lombok.Data;

@Data
public class GroupPermissionAdminDto extends BasicAdminDto {
    private String name;
    private String description;
}
