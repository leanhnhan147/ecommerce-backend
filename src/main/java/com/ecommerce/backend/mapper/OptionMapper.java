package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.option.OptionAdminDto;
import com.ecommerce.backend.dto.option.OptionDto;
import com.ecommerce.backend.form.option.CreateOptionForm;
import com.ecommerce.backend.form.option.UpdateOptionForm;
import com.ecommerce.backend.storage.entity.Option;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryOptionMapper.class})
public interface OptionMapper {

    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "code", target = "code")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    Option fromCreateOptionFormToEntity(CreateOptionForm createOptionForm);

    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "code", target = "code")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateOptionFormToEntity(UpdateOptionForm updateOptionForm, @MappingTarget Option option);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "code", target = "code")
//    @Mapping(source = "categoryOptions", target = "categoryOptions", qualifiedByName = "fromEntityListToCategoryOptionDtoList")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    OptionAdminDto fromEntityToOptionAdminDto(Option option);

    @IterableMapping(elementTargetType = OptionAdminDto.class, qualifiedByName = "adminGetMapping")
    List<OptionAdminDto> fromEntityListToOptionAdminDtoList(List<Option> options);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "code", target = "code")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToOptionDto")
    OptionDto fromEntityToOptionDto(Option option);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "code", target = "code")
    @BeanMapping(ignoreByDefault = true)
    @Named("autoCompleteGetMapping")
    OptionDto fromEntityToOptionDtoAutoComplete(Option option);

    @IterableMapping(elementTargetType = OptionDto.class, qualifiedByName = "autoCompleteGetMapping")
    List<OptionDto> fromEntityListToOptionDtoAutoCompleteList(List<Option> options);

}
