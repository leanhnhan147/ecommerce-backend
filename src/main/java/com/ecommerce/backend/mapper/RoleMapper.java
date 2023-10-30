package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.Role.RoleAdminDto;
import com.ecommerce.backend.dto.Role.RoleDto;
import com.ecommerce.backend.dto.option.OptionAdminDto;
import com.ecommerce.backend.dto.option.OptionDto;
import com.ecommerce.backend.form.option.CreateOptionForm;
import com.ecommerce.backend.form.option.UpdateOptionForm;
import com.ecommerce.backend.form.role.CreateRoleForm;
import com.ecommerce.backend.form.role.UpdateRoleForm;
import com.ecommerce.backend.storage.entity.Option;
import com.ecommerce.backend.storage.entity.Role;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PermissionMapper.class})
public interface RoleMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    Role fromCreateRoleFormToEntity(CreateRoleForm createRoleForm);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateRoleFormToEntity(UpdateRoleForm updateRoleForm, @MappingTarget Role role);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "permissions", target = "permissions", qualifiedByName = "fromEntityListToPermissionDtoList")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    RoleAdminDto fromEntityToRoleAdminDto(Role role);

    @IterableMapping(elementTargetType = RoleAdminDto.class, qualifiedByName = "adminGetMapping")
    List<RoleAdminDto> fromEntityListToRoleAdminDtoList(List<Role> roles);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToRoleDto")
    RoleDto fromEntityToRoleDto(Role role);
}
