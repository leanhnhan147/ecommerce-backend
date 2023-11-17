package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.inventory.InventoryAdminDto;
import com.ecommerce.backend.dto.optionValue.OptionValueAdminDto;
import com.ecommerce.backend.form.inventory.CreateInventoryForm;
import com.ecommerce.backend.form.inventory.UpdateInventoryForm;
import com.ecommerce.backend.form.optionValue.CreateOptionValueForm;
import com.ecommerce.backend.form.optionValue.UpdateOptionValueForm;
import com.ecommerce.backend.storage.entity.Inventory;
import com.ecommerce.backend.storage.entity.OptionValue;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductVariationMapper.class, UserMapper.class})
public interface InventoryMapper {

//    @Mapping(source = "originalPrice", target = "originalPrice")
//    @Mapping(source = "quantity", target = "quantity")
//    @BeanMapping(ignoreByDefault = true)
//    @Named("adminCreateMapping")
//    Inventory fromCreateInventoryFormToEntity(CreateInventoryForm createInventoryForm);

    @Mapping(source = "originalPrice", target = "originalPrice")
    @Mapping(source = "quantity", target = "quantity")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateInventoryFormToEntity(UpdateInventoryForm updateInventoryForm, @MappingTarget Inventory inventory);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "originalPrice", target = "originalPrice")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "sku", target = "sku")
    @Mapping(source = "importTime", target = "importTime")
    @Mapping(source = "productVariation", target = "productVariation", qualifiedByName = "fromEntityToProductVariationDto")
    @Mapping(source = "user", target = "user", qualifiedByName = "fromEntityToUserDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    InventoryAdminDto fromEntityToInventoryAdminDto(Inventory inventory);

    @IterableMapping(elementTargetType = InventoryAdminDto.class, qualifiedByName = "adminGetMapping")
    List<InventoryAdminDto> fromEntityListToInventoryAdminDtoList(List<Inventory> inventories);
}
