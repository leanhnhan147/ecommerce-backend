package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.productImage.ProductImageDto;
import com.ecommerce.backend.dto.productVariationOptionValue.ProductVariationOptionValueDto;
import com.ecommerce.backend.storage.entity.ProductImage;
import com.ecommerce.backend.storage.entity.ProductVariationOptionValue;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MediaResourceMapper.class, OptionValueImageMapper.class})
public interface ProductImageMapper {

    @Mapping(source = "mediaResource", target = "mediaResource", qualifiedByName = "fromEntityToMediaResourceDto")
    @Mapping(source = "optionValueImage", target = "optionValueImage", qualifiedByName = "fromEntityToOptionValueImageDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProductImageDto")
    ProductImageDto fromEntityToProductImageDto(ProductImage productImage);

    @IterableMapping(elementTargetType = ProductImageDto.class, qualifiedByName = "fromEntityToProductImageDto")
    @Named("fromEntityListToProductImageDtoList")
    List<ProductImageDto> fromEntityListToProductImageDtoList(List<ProductImage> productImages);

}