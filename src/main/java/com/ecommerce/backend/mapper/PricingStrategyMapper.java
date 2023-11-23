package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.pricingStrategy.PricingStrategyAdminDto;
import com.ecommerce.backend.dto.pricingStrategy.PricingStrategyDto;
import com.ecommerce.backend.form.pricingStrategy.CreatePricingStrategyForm;
import com.ecommerce.backend.form.pricingStrategy.UpdatePricingStrategyForm;
import com.ecommerce.backend.storage.entity.PricingStrategy;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductVariationMapper.class, UserMapper.class})
public interface PricingStrategyMapper {

//    @Mapping(source = "originalPrice", target = "originalPrice")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "discountedPrice", target = "discountedPrice")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "state", target = "state")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    PricingStrategy fromCreatePricingStrategyFormToEntity(CreatePricingStrategyForm createPricingStrategyForm);

//    @Mapping(source = "originalPrice", target = "originalPrice")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "discountedPrice", target = "discountedPrice")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "state", target = "state")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdatePricingStrategyFormToEntity(UpdatePricingStrategyForm updatePricingStrategyForm, @MappingTarget PricingStrategy pricingStrategy);

    @Mapping(source = "id", target = "id")
//    @Mapping(source = "originalPrice", target = "originalPrice")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "discountedPrice", target = "discountedPrice")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "productVariation", target = "productVariation", qualifiedByName = "fromEntityToProductVariationDto")
    @Mapping(source = "user", target = "user", qualifiedByName = "fromEntityToUserDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    PricingStrategyAdminDto fromEntityToPricingStrategyAdminDto(PricingStrategy pricingStrategy);

    @IterableMapping(elementTargetType = PricingStrategyAdminDto.class, qualifiedByName = "adminGetMapping")
    List<PricingStrategyAdminDto> fromEntityListToPricingStrategyAdminDtoList(List<PricingStrategy> pricingStrategies);

    @Mapping(source = "id", target = "id")
//    @Mapping(source = "originalPrice", target = "originalPrice")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "discountedPrice", target = "discountedPrice")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "state", target = "state")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPricingStrategyAutoCompleteDto")
    PricingStrategyDto fromEntityToPricingStrategyAutoCompleteDto(PricingStrategy pricingStrategy);

    @IterableMapping(elementTargetType = PricingStrategyDto.class, qualifiedByName = "fromEntityToPricingStrategyAutoCompleteDto")
    List<PricingStrategyDto> fromEntityListToPricingStrategyAutoCompleteDtoList(List<PricingStrategy> pricingStrategies);

}
