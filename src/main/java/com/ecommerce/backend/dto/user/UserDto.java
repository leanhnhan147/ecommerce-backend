package com.ecommerce.backend.dto.user;

import com.ecommerce.backend.dto.Role.RoleDto;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String avatar;
    private Date birhday;
    private String phone;
    private String email;
    private String username;
    private RoleDto role;
}
