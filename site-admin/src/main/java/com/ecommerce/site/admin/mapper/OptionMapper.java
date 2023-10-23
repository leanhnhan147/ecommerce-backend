package com.ecommerce.site.admin.mapper;

import com.ecommerce.site.admin.dto.option.OptionAdminDto;
import com.ecommerce.site.admin.dto.option.OptionDto;
import com.ecommerce.site.admin.form.option.CreateOptionForm;
import com.ecommerce.site.admin.form.option.UpdateOptionForm;
import com.ecommerce.site.admin.storage.entity.Option;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class})
public interface OptionMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "displayName", target = "displayName")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    Option fromCreateOptionFormToEntity(CreateOptionForm createOptionForm);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "displayName", target = "displayName")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateOptionFormToEntity(UpdateOptionForm updateOptionForm, @MappingTarget Option option);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToCategoryDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    OptionAdminDto fromEntityToOptionAdminDto(Option option);

    @IterableMapping(elementTargetType = OptionAdminDto.class, qualifiedByName = "adminGetMapping")
    List<OptionAdminDto> fromEntityListToOptionAdminDtoList(List<Option> options);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "displayName", target = "displayName")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToOptionDto")
    OptionDto fromEntityToOptionDto(Option option);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "displayName", target = "displayName")
    @BeanMapping(ignoreByDefault = true)
    @Named("autoCompleteGetMapping")
    OptionDto fromEntityToOptionDtoAutoComplete(Option option);

    @IterableMapping(elementTargetType = OptionDto.class, qualifiedByName = "autoCompleteGetMapping")
    List<OptionDto> fromEntityListToOptionDtoAutoCompleteList(List<Option> options);

}
