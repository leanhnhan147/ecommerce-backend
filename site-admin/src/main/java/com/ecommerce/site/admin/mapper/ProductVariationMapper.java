package com.ecommerce.site.admin.mapper;

import com.ecommerce.site.admin.dto.productVariation.ProductVariationAdminDto;
import com.ecommerce.site.admin.dto.productVariation.ProductVariationDto;
import com.ecommerce.site.admin.form.productVariation.CreateProductVariationForm;
import com.ecommerce.site.admin.form.productVariation.UpdateProductVariationForm;
import com.ecommerce.site.admin.storage.entity.ProductVariation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductMapper.class, ProductVariationOptionValueMapper.class})
public interface ProductVariationMapper {

    @Mapping(source = "price", target = "price")
    @Mapping(source = "state", target = "state")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    ProductVariation fromCreateProductVariationFormToEntity(CreateProductVariationForm createProductVariationForm);

    @Mapping(source = "price", target = "price")
    @Mapping(source = "state", target = "state")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateProductVariationFormToEntity(UpdateProductVariationForm updateProductVariationForm, @MappingTarget ProductVariation productVariation);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "state", target = "state")
//    @Mapping(source = "product", target = "product", qualifiedByName = "fromEntityToProductDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    ProductVariationAdminDto fromEntityToProductVariationAdminDto(ProductVariation productVariation);

    @IterableMapping(elementTargetType = ProductVariationAdminDto.class, qualifiedByName = "adminGetMapping")
    List<ProductVariationAdminDto> fromEntityListToProductVariationAdminDtoList(List<ProductVariation> productVariations);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "productVariationOptionValues", target = "productVariationOptionValues", qualifiedByName = "fromEntityListToProductVariationOptionValueDtoList")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProductVariationDto")
    ProductVariationDto fromEntityToProductVariationDto(ProductVariation productVariation);

    @IterableMapping(elementTargetType = ProductVariationDto.class, qualifiedByName = "fromEntityToProductVariationDto")
    @Named("fromEntityListToProductVariationDtoList")
    List<ProductVariationDto> fromEntityListToProductVariationDtoList(List<ProductVariation> productVariations);

}
