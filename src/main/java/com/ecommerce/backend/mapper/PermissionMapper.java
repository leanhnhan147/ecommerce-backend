package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.option.OptionDto;
import com.ecommerce.backend.dto.permission.PermissionAdminDto;
import com.ecommerce.backend.dto.permission.PermissionDto;
import com.ecommerce.backend.form.permission.CreatePermissionForm;
import com.ecommerce.backend.form.permission.UpdatePermissionForm;
import com.ecommerce.backend.storage.entity.Option;
import com.ecommerce.backend.storage.entity.Permission;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GroupPermissionMapper.class})
public interface PermissionMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "code", target = "code")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    Permission fromCreatePermissionFormToEntity(CreatePermissionForm createPermissionForm);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "code", target = "code")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdatePermissionFormToEntity(UpdatePermissionForm updatePermissionForm, @MappingTarget Permission permission);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "groupPermission", target = "groupPermission", qualifiedByName = "fromEntityToGroupPermissionDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    PermissionAdminDto fromEntityToPermissionAdminDto(Permission permission);

    @IterableMapping(elementTargetType = PermissionAdminDto.class, qualifiedByName = "adminGetMapping")
    List<PermissionAdminDto> fromEntityListToPermissionAdminDtoList(List<Permission> permissions);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "code", target = "code")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPermissionDtoComplete")
    PermissionDto fromEntityToPermissionDtoComplete(Permission permission);

    @IterableMapping(elementTargetType = PermissionDto.class, qualifiedByName = "fromEntityToPermissionDtoComplete")
    List<PermissionDto> fromEntityListToPermissionDtoAutoCompleteList(List<Permission> permissions);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "groupPermission", target = "groupPermission", qualifiedByName = "fromEntityToGroupPermissionDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPermissionDto")
    PermissionDto fromEntityToPermissionDto(Permission permission);

    @IterableMapping(elementTargetType = PermissionDto.class, qualifiedByName = "fromEntityToPermissionDto")
    @Named("fromEntityListToPermissionDtoList")
    List<PermissionDto> fromEntityListToPermissionDtoList(List<Permission> permissions);

}
