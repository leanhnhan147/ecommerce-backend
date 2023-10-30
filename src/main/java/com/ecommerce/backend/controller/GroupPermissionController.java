package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.groupPermission.GroupPermissionAdminDto;
import com.ecommerce.backend.form.groupPermission.CreateGroupPermissionForm;
import com.ecommerce.backend.form.groupPermission.UpdateGroupPermissionForm;
import com.ecommerce.backend.service.GroupPermissionService;
import com.ecommerce.backend.storage.criteria.GroupPermissionCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/group-permission")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class GroupPermissionController {

    @Autowired
    GroupPermissionService groupPermissionService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<GroupPermissionAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<GroupPermissionAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(groupPermissionService.getGroupPermissionById(id));
        apiMessageDto.setMessage("Get group permission success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<GroupPermissionAdminDto>>> getList(GroupPermissionCriteria groupPermissionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<GroupPermissionAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(groupPermissionService.getGroupPermissionList(groupPermissionCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list group permission success");
        return responseListDtoApiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateGroupPermissionForm createGroupPermissionForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        groupPermissionService.createGroupPermission(createGroupPermissionForm);
        apiMessageDto.setMessage("Create group permission success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateGroupPermissionForm updateGroupPermissionForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        groupPermissionService.updateGroupPermission(updateGroupPermissionForm);
        apiMessageDto.setMessage("Update group permission success");
        return apiMessageDto;
    }
}
