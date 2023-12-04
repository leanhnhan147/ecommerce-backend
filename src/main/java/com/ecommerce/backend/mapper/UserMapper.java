package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.customer.CustomerDto;
import com.ecommerce.backend.dto.user.UserAdminDto;
import com.ecommerce.backend.dto.user.UserDto;
import com.ecommerce.backend.form.user.CreateUserForm;
import com.ecommerce.backend.form.user.UpdateProfileUserForm;
import com.ecommerce.backend.form.user.UpdateUserForm;
import com.ecommerce.backend.storage.entity.Customer;
import com.ecommerce.backend.storage.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {RoleMapper.class})
public interface UserMapper {

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "birhday", target = "birhday")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    User fromCreateUserFormToEntity(CreateUserForm createUserForm);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "birhday", target = "birhday")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateUserFormToEntity(UpdateUserForm updateUserForm, @MappingTarget User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "birhday", target = "birhday")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "role", target = "role", qualifiedByName = "fromEntityToRoleDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    UserAdminDto fromEntityToUserAdminDto(User user);

    @IterableMapping(elementTargetType = UserAdminDto.class, qualifiedByName = "adminGetMapping")
    List<UserAdminDto> fromEntityListToUserAdminDtoList(List<User> users);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToUserDtoAutoComplete")
    UserDto fromEntityToUserDtoAutoComplete(User user);

    @IterableMapping(elementTargetType = UserDto.class, qualifiedByName = "fromEntityToUserDtoAutoComplete")
    List<UserDto> fromEntityListToUserDtoAutoCompleteList(List<User> users);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToUserDto")
    UserDto fromEntityToUserDto(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "birhday", target = "birhday")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "role", target = "role", qualifiedByName = "fromEntityToRoleDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToUserProfileDto")
    UserDto fromEntityToLoginDto(User user);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "birhday", target = "birhday")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToUserProfileDto")
    UserDto fromEntityToUserProfileDto(User user);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "birhday", target = "birhday")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromUpdateProfileUserFormToEntity")
    void fromUpdateProfileUserFormToEntity(UpdateProfileUserForm updateProfileUserForm, @MappingTarget User user);
}
