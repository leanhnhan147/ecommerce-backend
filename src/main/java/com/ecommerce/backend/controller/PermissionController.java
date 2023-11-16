package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.permission.PermissionAdminDto;
import com.ecommerce.backend.dto.permission.PermissionDto;
import com.ecommerce.backend.form.permission.CreatePermissionForm;
import com.ecommerce.backend.form.permission.UpdatePermissionForm;
import com.ecommerce.backend.service.PermissionService;
import com.ecommerce.backend.storage.criteria.PermissionCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/permission")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<PermissionAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<PermissionAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(permissionService.getPermissionById(id));
        apiMessageDto.setMessage("Get permission success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<PermissionAdminDto>>> getList(PermissionCriteria permissionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<PermissionAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(permissionService.getPermissionList(permissionCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list permission success");
        return responseListDtoApiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<PermissionDto>> getListAutoComplete(PermissionCriteria permissionCriteria) {
        ApiMessageDto<List<PermissionDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(permissionService.getPermissionListAutoComplete(permissionCriteria));
        apiMessageDto.setMessage("Get list auto complete success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreatePermissionForm createPermissionForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        permissionService.createPermission(createPermissionForm);
        apiMessageDto.setMessage("Create permission success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdatePermissionForm updatePermissionForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        permissionService.updatePermission(updatePermissionForm);
        apiMessageDto.setMessage("Update permission success");
        return apiMessageDto;
    }
}
