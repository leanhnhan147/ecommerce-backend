package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.cartItem.CartItemDto;
import com.ecommerce.backend.storage.entity.CartItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductVariationMapper.class})
public interface CartItemMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "productVariation", target = "productVariation", qualifiedByName = "fromEntityToProductVariationDtoForCartItem")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCartItemDto")
    CartItemDto fromEntityToCartItemDto(CartItem cartItem);

    @IterableMapping(elementTargetType = CartItemDto.class, qualifiedByName = "fromEntityToCartItemDto")
    List<CartItemDto> fromEntityListToCartItemDtoList(List<CartItem> cartItems);

}
