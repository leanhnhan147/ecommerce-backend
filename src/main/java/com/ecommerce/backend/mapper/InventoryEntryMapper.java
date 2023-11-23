package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryAdminDto;
import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryDto;
import com.ecommerce.backend.form.inventoryEntry.CreateInventoryEntryForm;
import com.ecommerce.backend.form.inventoryEntry.UpdateInventoryEntryForm;
import com.ecommerce.backend.storage.entity.InventoryEntry;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProviderMapper.class, UserMapper.class})
public interface InventoryEntryMapper {

    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "invoiceCode", target = "invoiceCode")
    @Mapping(source = "description", target = "description")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    InventoryEntry fromCreateInventoryEntryFormToEntity(CreateInventoryEntryForm createInventoryEntryForm);

    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "invoiceCode", target = "invoiceCode")
    @Mapping(source = "description", target = "description")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateInventoryEntryFormToEntity(UpdateInventoryEntryForm updateInventoryEntryForm, @MappingTarget InventoryEntry inventoryEntry);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "invoiceCode", target = "invoiceCode")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "provider", target = "provider", qualifiedByName = "fromEntityToProviderDto")
    @Mapping(source = "user", target = "user", qualifiedByName = "fromEntityToUserDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    InventoryEntryAdminDto fromEntityToInventoryEntryAdminDto(InventoryEntry inventoryEntry);

    @IterableMapping(elementTargetType = InventoryEntryAdminDto.class, qualifiedByName = "adminGetMapping")
    List<InventoryEntryAdminDto> fromEntityListToInventoryEntryAdminDtoList(List<InventoryEntry> inventories);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "invoiceCode", target = "invoiceCode")
    @Mapping(source = "description", target = "description")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToInventoryEntryDtoAutoComplete")
    InventoryEntryDto fromEntityToInventoryEntryDtoAutoComplete(InventoryEntry inventoryEntry);

    @IterableMapping(elementTargetType = InventoryEntryDto.class, qualifiedByName = "fromEntityToInventoryEntryDtoAutoComplete")
    List<InventoryEntryDto> fromEntityListToInventoryEntryDtoAutoCompleteList(List<InventoryEntry> inventories);

}
