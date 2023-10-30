package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.Role.RoleAdminDto;
import com.ecommerce.backend.form.role.CreateRoleForm;
import com.ecommerce.backend.form.role.UpdateRoleForm;
import com.ecommerce.backend.storage.criteria.RoleCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    RoleAdminDto getRoleById(Long id);

    ResponseListDto<List<RoleAdminDto>> getRoleList(RoleCriteria roleCriteria, Pageable pageable);

    void createRole(CreateRoleForm createRoleForm);

    void updateRole(UpdateRoleForm updateRoleForm);
}
