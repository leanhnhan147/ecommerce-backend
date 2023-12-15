package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.orderDetail.OrderDetailDto;
import com.ecommerce.backend.storage.entity.OrderDetail;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductVariationMapper.class})
public interface OrderDetailMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "productVariation", target = "productVariation", qualifiedByName = "fromEntityToProductVariationDtoForCartItem")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToOrderDetailDto")
    OrderDetailDto fromEntityToOrderDetailDto(OrderDetail orderDetail);

    @IterableMapping(elementTargetType = OrderDetailDto.class, qualifiedByName = "fromEntityToOrderDetailDto")
    List<OrderDetailDto> fromEntityListToOrderDetailDtoList(List<OrderDetail> orderDetails);
}
