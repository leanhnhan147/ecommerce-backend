package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.product.ProductAdminDto;
import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.form.product.CreateProductForm;
import com.ecommerce.backend.form.product.UpdateProductForm;
import com.ecommerce.backend.storage.entity.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class, ProductVariationMapper.class, ProductImageMapper.class})
public interface ProductMapper {

    @Mapping(source = "name", target = "name")
//    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    Product fromCreateProductFormToEntity(CreateProductForm createProductForm);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateProductFormToEntity(UpdateProductForm updateProductForm, @MappingTarget Product product);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "stock", target = "stock")
    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToCategoryDto")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    ProductAdminDto fromEntityToProductAdminDto(Product product);

    @IterableMapping(elementTargetType = ProductAdminDto.class, qualifiedByName = "adminGetMapping")
    List<ProductAdminDto> fromEntityListToProductAdminDtoList(List<Product> products);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "stock", target = "stock")
    @Mapping(source = "soldCount", target = "soldCount")
    @Mapping(source = "ratingCount", target = "ratingCount")
    @Mapping(source = "averageRating", target = "averageRating")
    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToCategoryDto")
    @Mapping(source = "productVariations", target = "productVariations", qualifiedByName = "fromEntityListToProductVariationDtoList")
    @Mapping(source = "productImages", target = "productImages", qualifiedByName = "fromEntityListToProductImageDtoList")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProductDto")
    ProductDto fromEntityToProductDto(Product product);
}
