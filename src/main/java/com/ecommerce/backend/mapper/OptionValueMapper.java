package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.optionValue.OptionValueAdminDto;
import com.ecommerce.backend.dto.optionValue.OptionValueDto;
import com.ecommerce.backend.form.optionValue.CreateOptionValueForm;
import com.ecommerce.backend.form.optionValue.UpdateOptionValueForm;
import com.ecommerce.backend.storage.entity.OptionValue;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {OptionMapper.class})
public interface OptionValueMapper {

    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "code", target = "code")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    OptionValue fromCreateOptionValueFormToEntity(CreateOptionValueForm createOptionValueForm);

    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "code", target = "code")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateOptionValueFormToEntity(UpdateOptionValueForm updateOptionValueForm, @MappingTarget OptionValue optionValue);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "option", target = "option", qualifiedByName = "fromEntityToOptionDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    OptionValueAdminDto fromEntityToOptionValueAdminDto(OptionValue optionValue);

    @IterableMapping(elementTargetType = OptionValueAdminDto.class, qualifiedByName = "adminGetMapping")
    List<OptionValueAdminDto> fromEntityListToOptionValueAdminDtoList(List<OptionValue> optionValues);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "code", target = "code")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToOptionValueDtoAutoComplete")
    OptionValueDto fromEntityToOptionValueDtoAutoComplete(OptionValue optionValue);

    @IterableMapping(elementTargetType = OptionValueDto.class, qualifiedByName = "fromEntityToOptionValueDtoAutoComplete")
    List<OptionValueDto> fromEntityListToOptionValueDtoAutoCompleteList(List<OptionValue> optionValues);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "option", target = "option", qualifiedByName = "fromEntityToOptionDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToOptionValueDto")
    OptionValueDto fromEntityToOptionValueDto(OptionValue optionValue);
}
