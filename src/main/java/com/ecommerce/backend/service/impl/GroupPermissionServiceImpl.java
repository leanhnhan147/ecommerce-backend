package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.groupPermission.GroupPermissionAdminDto;
import com.ecommerce.backend.dto.groupPermission.GroupPermissionDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.groupPermission.CreateGroupPermissionForm;
import com.ecommerce.backend.form.groupPermission.UpdateGroupPermissionForm;
import com.ecommerce.backend.mapper.GroupPermissionMapper;
import com.ecommerce.backend.repository.GroupPermissionRepository;
import com.ecommerce.backend.service.GroupPermissionService;
import com.ecommerce.backend.storage.criteria.GroupPermissionCriteria;
import com.ecommerce.backend.storage.entity.Category;
import com.ecommerce.backend.storage.entity.GroupPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupPermissionServiceImpl implements GroupPermissionService {

    @Autowired
    GroupPermissionRepository groupPermissionRepository;

    @Autowired
    GroupPermissionMapper groupPermissionMapper;

    @Override
    public GroupPermissionAdminDto getGroupPermissionById(Long id) {
        GroupPermission groupPermission = groupPermissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found group permission"));
        return groupPermissionMapper.fromEntityToGroupPermissionAdminDto(groupPermission);
    }

    @Override
    public ResponseListDto<List<GroupPermissionAdminDto>> getGroupPermissionList(GroupPermissionCriteria groupPermissionCriteria, Pageable pageable) {
        Page<GroupPermission> groupPermissions = groupPermissionRepository.findAll(groupPermissionCriteria.getCriteria(), pageable);
        ResponseListDto<List<GroupPermissionAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(groupPermissionMapper.fromEntityListToGroupPermissionAdminDtoList(groupPermissions.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(groupPermissions.getTotalPages());
        responseListDto.setTotalElements(groupPermissions.getTotalElements());
        return responseListDto;
    }

    @Override
    public List<GroupPermissionDto> getGroupPermissionListAutoComplete(GroupPermissionCriteria groupPermissionCriteria) {
        List<GroupPermission> groupPermissions = groupPermissionRepository.findAll(groupPermissionCriteria.getCriteria());
        return groupPermissionMapper.fromEntityListToGroupPermissionDtoAutoCompleteList(groupPermissions);
    }

    @Override
    public void createGroupPermission(CreateGroupPermissionForm createGroupPermissionForm) {
        GroupPermission groupPermission = groupPermissionMapper.fromCreateGroupPermissionFormToEntity(createGroupPermissionForm);
        groupPermissionRepository.save(groupPermission);
    }

    @Override
    public void updateGroupPermission(UpdateGroupPermissionForm updateGroupPermissionForm) {
        GroupPermission groupPermission = groupPermissionRepository.findById(updateGroupPermissionForm.getId()).orElse(null);
        if(groupPermission == null){
            throw new NotFoundException("Not found group permission");
        }
        groupPermissionMapper.fromUpdateGroupPermissionFormToEntity(updateGroupPermissionForm, groupPermission);
        groupPermissionRepository.save(groupPermission);
    }
}
