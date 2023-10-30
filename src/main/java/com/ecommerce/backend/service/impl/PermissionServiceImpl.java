package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.option.OptionAdminDto;
import com.ecommerce.backend.dto.permission.PermissionAdminDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.permission.CreatePermissionForm;
import com.ecommerce.backend.form.permission.UpdatePermissionForm;
import com.ecommerce.backend.mapper.PermissionMapper;
import com.ecommerce.backend.repository.GroupPermissionRepository;
import com.ecommerce.backend.repository.PermissionRepository;
import com.ecommerce.backend.service.PermissionService;
import com.ecommerce.backend.storage.criteria.PermissionCriteria;
import com.ecommerce.backend.storage.entity.GroupPermission;
import com.ecommerce.backend.storage.entity.Option;
import com.ecommerce.backend.storage.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    GroupPermissionRepository groupPermissionRepository;

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public PermissionAdminDto getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found permission"));
        return permissionMapper.fromEntityToPermissionAdminDto(permission);
    }

    @Override
    public ResponseListDto<List<PermissionAdminDto>> getPermissionList(PermissionCriteria permissionCriteria, Pageable pageable) {
        Page<Permission> permissions = permissionRepository.findAll(permissionCriteria.getCriteria(), pageable);
        ResponseListDto<List<PermissionAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(permissionMapper.fromEntityListToPermissionAdminDtoList(permissions.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(permissions.getTotalPages());
        responseListDto.setTotalElements(permissions.getTotalElements());
        return responseListDto;
    }

    @Override
    public void createPermission(CreatePermissionForm createPermissionForm) {
        GroupPermission groupPermission = groupPermissionRepository.findById(createPermissionForm.getGroupPermissionId()).orElse(null);
        if(groupPermission == null){
            throw new NotFoundException("Not found group permission");
        }
        Permission permission = permissionMapper.fromCreatePermissionFormToEntity(createPermissionForm);
        permission.setGroupPermission(groupPermission);
        permissionRepository.save(permission);
    }

    @Override
    public void updatePermission(UpdatePermissionForm updatePermissionForm) {
        Permission permission = permissionRepository.findById(updatePermissionForm.getId()).orElse(null);
        if(permission == null){
            throw new NotFoundException("Not found permission");
        }
        permissionMapper.fromUpdatePermissionFormToEntity(updatePermissionForm, permission);
        permissionRepository.save(permission);
    }
}
