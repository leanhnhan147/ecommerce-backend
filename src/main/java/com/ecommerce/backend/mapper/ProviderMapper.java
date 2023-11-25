package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.option.OptionAdminDto;
import com.ecommerce.backend.dto.option.OptionDto;
import com.ecommerce.backend.dto.provider.ProviderAdminDto;
import com.ecommerce.backend.dto.provider.ProviderDto;
import com.ecommerce.backend.form.option.CreateOptionForm;
import com.ecommerce.backend.form.option.UpdateOptionForm;
import com.ecommerce.backend.form.provider.CreateProviderForm;
import com.ecommerce.backend.form.provider.UpdateProviderForm;
import com.ecommerce.backend.storage.entity.Option;
import com.ecommerce.backend.storage.entity.Provider;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProviderMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "address", target = "address")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    Provider fromCreateProviderFormToEntity(CreateProviderForm createProviderForm);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "address", target = "address")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateProviderFormToEntity(UpdateProviderForm updateProviderForm, @MappingTarget Provider provider);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    ProviderAdminDto fromEntityToProviderAdminDto(Provider provider);

    @IterableMapping(elementTargetType = ProviderAdminDto.class, qualifiedByName = "adminGetMapping")
    List<ProviderAdminDto> fromEntityListToProviderAdminDtoList(List<Provider> providers);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "address", target = "address")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProviderDtoAutoComplete")
    ProviderDto fromEntityToProviderDtoAutoComplete(Provider provider);

    @IterableMapping(elementTargetType = ProviderDto.class, qualifiedByName = "fromEntityToProviderDtoAutoComplete")
    List<ProviderDto> fromEntityListToProviderDtoAutoCompleteList(List<Provider> providers);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "address", target = "address")
    @Named("fromEntityToProviderDto")
    ProviderDto fromEntityToProviderDto(Provider provider);
}
