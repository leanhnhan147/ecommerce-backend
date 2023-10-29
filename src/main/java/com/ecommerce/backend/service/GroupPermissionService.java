package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.groupPermission.GroupPermissionAdminDto;
import com.ecommerce.backend.form.groupPermission.CreateGroupPermissionForm;
import com.ecommerce.backend.form.groupPermission.UpdateGroupPermissionForm;
import com.ecommerce.backend.storage.criteria.GroupPermissionCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupPermissionService {

    GroupPermissionAdminDto getGroupPermissionById(Long id);

    ResponseListDto<List<GroupPermissionAdminDto>> getGroupPermissionList(GroupPermissionCriteria groupPermissionCriteria, Pageable pageable);

    void createGroupPermission(CreateGroupPermissionForm createGroupPermissionForm);

    void updateGroupPermission(UpdateGroupPermissionForm updateGroupPermissionForm);
}
