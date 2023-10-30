package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.groupPermission.GroupPermissionAdminDto;
import com.ecommerce.backend.dto.groupPermission.GroupPermissionDto;
import com.ecommerce.backend.form.groupPermission.CreateGroupPermissionForm;
import com.ecommerce.backend.form.groupPermission.UpdateGroupPermissionForm;
import com.ecommerce.backend.storage.entity.GroupPermission;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GroupPermissionMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    GroupPermission fromCreateGroupPermissionFormToEntity(CreateGroupPermissionForm createGroupPermissionForm);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateGroupPermissionFormToEntity(UpdateGroupPermissionForm updateGroupPermissionForm, @MappingTarget GroupPermission groupPermission);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    GroupPermissionAdminDto fromEntityToGroupPermissionAdminDto(GroupPermission groupPermission);

    @IterableMapping(elementTargetType = GroupPermissionAdminDto.class, qualifiedByName = "adminGetMapping")
    List<GroupPermissionAdminDto> fromEntityListToGroupPermissionAdminDtoList(List<GroupPermission> groupPermissions);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToGroupPermissionDto")
    GroupPermissionDto fromEntityToGroupPermissionDto(GroupPermission groupPermission);
}
