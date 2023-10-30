package com.ecommerce.backend.controller;


import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.Role.RoleAdminDto;
import com.ecommerce.backend.form.role.CreateRoleForm;
import com.ecommerce.backend.form.role.UpdateRoleForm;
import com.ecommerce.backend.service.RoleService;
import com.ecommerce.backend.storage.criteria.RoleCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/role")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<RoleAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<RoleAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(roleService.getRoleById(id));
        apiMessageDto.setMessage("Get role success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<RoleAdminDto>>> getList(RoleCriteria roleCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<RoleAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(roleService.getRoleList(roleCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list role success");
        return responseListDtoApiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateRoleForm createRoleForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        roleService.createRole(createRoleForm);
        apiMessageDto.setMessage("Create role success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateRoleForm updateRoleForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        roleService.updateRole(updateRoleForm);
        apiMessageDto.setMessage("Update role success");
        return apiMessageDto;
    }
}
