package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.orderTracking.OrderTrackingDto;
import com.ecommerce.backend.storage.entity.OrderTracking;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderTrackingMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "createdDate", target = "createdDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToOrderTrackingDto")
    OrderTrackingDto fromEntityToOrderTrackingDto(OrderTracking orderTracking);

    @IterableMapping(elementTargetType = OrderTrackingDto.class, qualifiedByName = "fromEntityToOrderTrackingDto")
    List<OrderTrackingDto> fromEntityListToOrderTrackingDtoList(List<OrderTracking> orderTrackings);
}
