package com.ecommerce.backend.dto.permission;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.groupPermission.GroupPermissionDto;
import lombok.Data;

@Data
public class PermissionAdminDto extends BasicAdminDto {
    private String name;
    private String action;
    private String description;
    private String code;
    private GroupPermissionDto groupPermission;
}
