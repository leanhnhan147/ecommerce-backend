package com.ecommerce.backend.dto.Role;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.permission.PermissionDto;
import lombok.Data;

import java.util.List;

@Data
public class RoleAdminDto extends BasicAdminDto {
    private String name;
    private String description;
    private List<PermissionDto> permissions;
}
