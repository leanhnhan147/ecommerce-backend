package com.ecommerce.site.admin.mapper;

import com.ecommerce.site.admin.dto.productVariationOptionValue.ProductVariationOptionValueDto;
import com.ecommerce.site.admin.storage.entity.ProductVariationOptionValue;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {OptionValueMapper.class})
public interface ProductVariationOptionValueMapper {

    @Mapping(source = "optionValue", target = "optionValue", qualifiedByName = "fromEntityToOptionValueDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProductVariationOptionValueDto")
    ProductVariationOptionValueDto fromEntityToProductVariationOptionValueDto(ProductVariationOptionValue productVariationOptionValue);

    @IterableMapping(elementTargetType = ProductVariationOptionValueDto.class, qualifiedByName = "fromEntityToProductVariationOptionValueDto")
    @Named("fromEntityListToProductVariationOptionValueDtoList")
    List<ProductVariationOptionValueDto> fromEntityListToProductVariationOptionValueDtoList(List<ProductVariationOptionValue> productVariationOptionValues);

}
