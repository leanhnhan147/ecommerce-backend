package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.optionValueImage.OptionValueImageDto;
import com.ecommerce.backend.storage.entity.OptionValueImage;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {OptionValueMapper.class})
public interface OptionValueImageMapper {

    @Mapping(source = "optionValue", target = "optionValue", qualifiedByName = "fromEntityToOptionValueDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToOptionValueImageDto")
    OptionValueImageDto fromEntityToOptionValueImageDto(OptionValueImage optionValueImage);
}
