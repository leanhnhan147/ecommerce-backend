package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.permission.PermissionAdminDto;
import com.ecommerce.backend.dto.permission.PermissionDto;
import com.ecommerce.backend.form.permission.CreatePermissionForm;
import com.ecommerce.backend.form.permission.UpdatePermissionForm;
import com.ecommerce.backend.storage.criteria.PermissionCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PermissionService {

    PermissionAdminDto getPermissionById(Long id);

    ResponseListDto<List<PermissionAdminDto>> getPermissionList(PermissionCriteria permissionCriteria, Pageable pageable);

    List<PermissionDto> getPermissionListAutoComplete(PermissionCriteria permissionCriteria);

    void createPermission(CreatePermissionForm createPermissionForm);

    void updatePermission(UpdatePermissionForm updatePermissionForm);
}
