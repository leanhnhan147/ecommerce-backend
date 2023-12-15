package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.Role.RoleAdminDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.role.CreateRoleForm;
import com.ecommerce.backend.form.role.UpdateRoleForm;
import com.ecommerce.backend.mapper.RoleMapper;
import com.ecommerce.backend.repository.PermissionRepository;
import com.ecommerce.backend.repository.RoleRepository;
import com.ecommerce.backend.service.RoleService;
import com.ecommerce.backend.storage.criteria.RoleCriteria;
import com.ecommerce.backend.storage.entity.Permission;
import com.ecommerce.backend.storage.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public RoleAdminDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found role"));
        return roleMapper.fromEntityToRoleAdminDto(role);
    }

    @Override
    public ResponseListDto<List<RoleAdminDto>> getRoleList(RoleCriteria roleCriteria, Pageable pageable) {
        Page<Role> roles = roleRepository.findAll(roleCriteria.getCriteria(), pageable);
        ResponseListDto<List<RoleAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(roleMapper.fromEntityListToRoleAdminDtoList(roles.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(roles.getTotalPages());
        responseListDto.setTotalElements(roles.getTotalElements());
        return responseListDto;
    }

    @Override
    public void createRole(CreateRoleForm createRoleForm) {
        Role role = roleMapper.fromCreateRoleFormToEntity(createRoleForm);
        List<Permission> permissions = new ArrayList<>();
        for (int i = 0; i < createRoleForm.getPermissionId().length; i++){
            Permission permission = permissionRepository.findById(createRoleForm.getPermissionId()[i]).orElse(null);
            if(permission != null){
                permissions.add(permission);
            }
        }
        role.setPermissions(permissions);
        roleRepository.save(role);
    }

    @Override
    public void updateRole(UpdateRoleForm updateRoleForm) {
        Role role = roleRepository.findById(updateRoleForm.getId()).orElse(null);
        if(role == null){
            throw new NotFoundException("Not found role");
        }
        roleMapper.fromUpdateRoleFormToEntity(updateRoleForm, role);

        List<Permission> permissions = new ArrayList<>();
        for (int i = 0; i < updateRoleForm.getPermissionId().length; i++){
            Permission permission = permissionRepository.findById(updateRoleForm.getPermissionId()[i]).orElse(null);
            if(permission != null){
                permissions.add(permission);
            }
        }
        role.setPermissions(permissions);
        roleRepository.save(role);
    }
}
