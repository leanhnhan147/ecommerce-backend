package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.categoryOption.CategoryOptionDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class})
public interface CategoryOptionMapper {

//    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToCategoryDto")
//    @BeanMapping(ignoreByDefault = true)
//    @Named("fromEntityToCategoryOptionDto")
//    CategoryOptionDto fromEntityToCategoryOptionDto(CategoryOption categoryOption);
//
//    @IterableMapping(elementTargetType = CategoryOptionDto.class, qualifiedByName = "fromEntityToCategoryOptionDto")
//    @Named("fromEntityListToCategoryOptionDtoList")
//    List<CategoryOptionDto> fromEntityListToCategoryOptionDtoList(List<CategoryOption> categoryOptions);
}
