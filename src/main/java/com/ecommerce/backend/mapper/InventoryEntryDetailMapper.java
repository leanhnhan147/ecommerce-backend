package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.inventoryEntryDetail.InventoryEntryDetailAdminDto;
import com.ecommerce.backend.dto.inventoryEntryDetail.InventoryEntryDetailDto;
import com.ecommerce.backend.storage.entity.InventoryEntryDetail;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductVariationMapper.class, InventoryEntryMapper.class})
public interface InventoryEntryDetailMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "originalPrice", target = "originalPrice")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "productVariation", target = "productVariation", qualifiedByName = "fromEntityToProductVariationDto")
    @Mapping(source = "inventoryEntry", target = "inventoryEntry", qualifiedByName = "fromEntityToInventoryEntryDtoAutoComplete")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    InventoryEntryDetailAdminDto fromEntityToInventoryEntryDetailAdminDto(InventoryEntryDetail inventoryEntryDetail);

    @IterableMapping(elementTargetType = InventoryEntryDetailAdminDto.class, qualifiedByName = "adminGetMapping")
    List<InventoryEntryDetailAdminDto> fromEntityListToInventoryEntryDetailAdminDtoList(List<InventoryEntryDetail> inventoryEntryDetails);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "originalPrice", target = "originalPrice")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "productVariation", target = "productVariation", qualifiedByName = "fromEntityToProductVariationDto")
    @Mapping(source = "inventoryEntry", target = "inventoryEntry", qualifiedByName = "fromEntityToInventoryEntryDtoAutoComplete")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToInventoryEntryDetailDtoAutoComplete")
    InventoryEntryDetailDto fromEntityToInventoryEntryDetailDtoAutoComplete(InventoryEntryDetail inventoryEntryDetail);

    @IterableMapping(elementTargetType = InventoryEntryDetailDto.class, qualifiedByName = "fromEntityToInventoryEntryDetailDtoAutoComplete")
    List<InventoryEntryDetailDto> fromEntityListToInventoryEntryDetailDtoAutoCompleteList(List<InventoryEntryDetail> inventoryEntryDetails);
}
