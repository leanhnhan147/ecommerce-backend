package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.customer.CustomerAdminDto;
import com.ecommerce.backend.dto.customer.CustomerDto;
import com.ecommerce.backend.dto.user.UserAdminDto;
import com.ecommerce.backend.form.customer.RegisterCustomerForm;
import com.ecommerce.backend.form.customer.UpdateCustomerForm;
import com.ecommerce.backend.form.customer.UpdateProfileCustomerForm;
import com.ecommerce.backend.form.user.UpdateProfileUserForm;
import com.ecommerce.backend.storage.entity.Customer;
import com.ecommerce.backend.storage.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromRegisterCustomerFormToEntity")
    Customer fromRegisterCustomerFormToEntity(RegisterCustomerForm registerCustomerForm);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "birhday", target = "birhday")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateCustomerFormToEntity(UpdateCustomerForm updateCustomerForm, @MappingTarget Customer customer);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "birhday", target = "birhday")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    CustomerAdminDto fromEntityToCustomerAdminDto(Customer customer);

    @IterableMapping(elementTargetType = CustomerAdminDto.class, qualifiedByName = "adminGetMapping")
    List<CustomerAdminDto> fromEntityListToCustomerAdminDtoList(List<Customer> customers);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCustomerDtoAutoComplete")
    CustomerDto fromEntityToCustomerDtoAutoComplete(Customer customer);

    @IterableMapping(elementTargetType = CustomerDto.class, qualifiedByName = "fromEntityToCustomerDtoAutoComplete")
    List<CustomerDto> fromEntityListToCustomerDtoAutoCompleteList(List<Customer> customers);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "birhday", target = "birhday")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToLoginCustomerDto")
    CustomerDto fromEntityToLoginCustomerDto(Customer customer);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "birhday", target = "birhday")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProfileCustomerDto")
    CustomerDto fromEntityToProfileCustomerDto(Customer customer);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "birhday", target = "birhday")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Named("fromUpdateProfileCustomerFormToEntity")
    void fromUpdateProfileCustomerFormToEntity(UpdateProfileCustomerForm updateProfileCustomerForm, @MappingTarget Customer customer);
}
