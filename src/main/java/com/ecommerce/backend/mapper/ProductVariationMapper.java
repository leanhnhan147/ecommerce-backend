package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.productVariation.ProductVariationAdminDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationCartItemDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationReviewDto;
import com.ecommerce.backend.form.productVariation.UpdateProductVariationForm;
import com.ecommerce.backend.storage.entity.ProductVariation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductMapper.class, ProductVariationOptionValueMapper.class})
public interface ProductVariationMapper {

//    @Mapping(source = "price", target = "price")
//    @Mapping(source = "state", target = "state")
//    @BeanMapping(ignoreByDefault = true)
//    @Named("adminCreateMapping")
//    ProductVariation fromCreateProductVariationFormToEntity(CreateProductVariationForm createProductVariationForm);

    @Mapping(source = "state", target = "state")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateProductVariationFormToEntity(UpdateProductVariationForm updateProductVariationForm, @MappingTarget ProductVariation productVariation);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "product", target = "product", qualifiedByName = "fromEntityToProductDtoForProductVariation")
    @Mapping(source = "productVariationOptionValues", target = "productVariationOptionValues", qualifiedByName = "fromEntityListToProductVariationOptionValueDtoList")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    ProductVariationAdminDto fromEntityToProductVariationAdminDto(ProductVariation productVariation);

    @IterableMapping(elementTargetType = ProductVariationAdminDto.class, qualifiedByName = "adminGetMapping")
    List<ProductVariationAdminDto> fromEntityListToProductVariationAdminDtoList(List<ProductVariation> productVariations);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "productVariationOptionValues", target = "productVariationOptionValues", qualifiedByName = "fromEntityListToProductVariationOptionValueDtoList")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProductVariationDto")
    ProductVariationDto fromEntityToProductVariationDto(ProductVariation productVariation);

    @IterableMapping(elementTargetType = ProductVariationDto.class, qualifiedByName = "fromEntityToProductVariationDto")
    @Named("fromEntityListToProductVariationDtoList")
    List<ProductVariationDto> fromEntityListToProductVariationDtoList(List<ProductVariation> productVariations);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "product", target = "product", qualifiedByName = "fromEntityToProductDtoForProductVariationCartItem")
    @Named("fromEntityToProductVariationDtoForCartItem")
    ProductVariationCartItemDto fromEntityToProductVariationDtoForCartItem(ProductVariation productVariation);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "product", target = "product", qualifiedByName = "fromEntityToProductDtoForProductVariationCartItem")
    @Named("fromEntityToProductVariationDtoForReview")
    ProductVariationReviewDto fromEntityToProductVariationDtoForReview(ProductVariation productVariation);

}
