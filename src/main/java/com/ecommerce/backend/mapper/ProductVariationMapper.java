package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.productVariation.ProductVariationAdminDto;
import com.ecommerce.backend.form.productVariation.CreateProductVariationForm;
import com.ecommerce.backend.form.productVariation.UpdateProductVariationForm;
import com.ecommerce.backend.storage.entity.ProductVariation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductMapper.class})
public interface ProductVariationMapper {

    @Mapping(source = "originPrice", target = "originPrice")
    @Mapping(source = "state", target = "state")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    ProductVariation fromCreateProductVariationFormToEntity(CreateProductVariationForm createProductVariationForm);

    @Mapping(source = "originPrice", target = "originPrice")
    @Mapping(source = "state", target = "state")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateProductVariationFormToEntity(UpdateProductVariationForm updateProductVariationForm, @MappingTarget ProductVariation productVariation);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "originPrice", target = "originPrice")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "product", target = "product", qualifiedByName = "fromEntityToProductDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    ProductVariationAdminDto fromEntityToProductVariationAdminDto(ProductVariation productVariation);

    @IterableMapping(elementTargetType = ProductVariationAdminDto.class, qualifiedByName = "adminGetMapping")
    List<ProductVariationAdminDto> fromEntityListToProductVariationAdminDtoList(List<ProductVariation> productVariations);

}
