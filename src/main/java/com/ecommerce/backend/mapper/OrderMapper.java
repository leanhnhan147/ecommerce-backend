package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.order.OrderAdminDto;
import com.ecommerce.backend.dto.order.OrderDto;
import com.ecommerce.backend.storage.entity.Order;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "shippingPrice", target = "shippingPrice")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "receiverName", target = "receiverName")
    @Mapping(source = "receiverPhone", target = "receiverPhone")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Named("adminGetMapping")
    OrderAdminDto fromEntityToOrderAdminDto(Order order);

    @IterableMapping(elementTargetType = OrderAdminDto.class, qualifiedByName = "adminGetMapping")
    List<OrderAdminDto> fromEntityListToOrderAdminDtoList(List<Order> orders);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "shippingPrice", target = "shippingPrice")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "receiverName", target = "receiverName")
    @Mapping(source = "receiverPhone", target = "receiverPhone")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToOrderDto")
    OrderDto fromEntityToOrderDto(Order order);

    @IterableMapping(elementTargetType = OrderDto.class, qualifiedByName = "fromEntityToOrderDto")
    List<OrderDto> fromEntityListToOrderDtoList(List<Order> orders);

}
