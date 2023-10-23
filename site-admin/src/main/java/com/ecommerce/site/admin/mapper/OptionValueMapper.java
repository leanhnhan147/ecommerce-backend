package com.ecommerce.site.admin.mapper;

import com.ecommerce.site.admin.dto.optionValue.OptionValueAdminDto;
import com.ecommerce.site.admin.dto.optionValue.OptionValueDto;
import com.ecommerce.site.admin.form.optionValue.CreateOptionValueForm;
import com.ecommerce.site.admin.form.optionValue.UpdateOptionValueForm;
import com.ecommerce.site.admin.storage.entity.OptionValue;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {OptionMapper.class})
public interface OptionValueMapper {

    @Mapping(source = "value", target = "value")
    @Mapping(source = "displayName", target = "displayName")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    OptionValue fromCreateOptionValueFormToEntity(CreateOptionValueForm createOptionValueForm);

    @Mapping(source = "value", target = "value")
    @Mapping(source = "displayName", target = "displayName")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateOptionValueFormToEntity(UpdateOptionValueForm updateOptionValueForm, @MappingTarget OptionValue optionValue);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "value", target = "value")
    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "option", target = "option", qualifiedByName = "fromEntityToOptionDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    OptionValueAdminDto fromEntityToOptionValueAdminDto(OptionValue optionValue);

    @IterableMapping(elementTargetType = OptionValueAdminDto.class, qualifiedByName = "adminGetMapping")
    List<OptionValueAdminDto> fromEntityListToOptionValueAdminDtoList(List<OptionValue> optionValues);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "value", target = "value")
    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "option", target = "option", qualifiedByName = "fromEntityToOptionDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToOptionValueDto")
    OptionValueDto fromEntityToOptionValueDto(OptionValue optionValue);

}
