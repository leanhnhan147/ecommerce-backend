package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.address.AddressDto;
import com.ecommerce.backend.form.address.CreateAddressForm;
import com.ecommerce.backend.form.address.UpdateAddressForm;
import com.ecommerce.backend.storage.entity.Address;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {NationMapper.class})
public interface AddressMapper {

    @Mapping(source = "receiverName", target = "receiverName")
    @Mapping(source = "receiverPhone", target = "receiverPhone")
    @Mapping(source = "addressDetail", target = "addressDetail")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateAddressFormToEntity")
    Address fromCreateAddressFormToEntity(CreateAddressForm createAddressForm);

    @Mapping(source = "receiverName", target = "receiverName")
    @Mapping(source = "receiverPhone", target = "receiverPhone")
    @Mapping(source = "addressDetail", target = "addressDetail")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromUpdateAddressFormToEntity")
    void fromUpdateAddressFormToEntity(UpdateAddressForm updateAddressForm, @MappingTarget Address address);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "receiverName", target = "receiverName")
    @Mapping(source = "receiverPhone", target = "receiverPhone")
    @Mapping(source = "addressDetail", target = "addressDetail")
    @Mapping(source = "ward", target = "ward", qualifiedByName = "fromEntityToNationDtoAutoComplete")
    @Mapping(source = "district", target = "district", qualifiedByName = "fromEntityToNationDtoAutoComplete")
    @Mapping(source = "province", target = "province", qualifiedByName = "fromEntityToNationDtoAutoComplete")
    @Mapping(source = "isDefault", target = "isDefault")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToAddressDto")
    AddressDto fromEntityToAddressDto(Address address);

    @IterableMapping(elementTargetType = AddressDto.class, qualifiedByName = "fromEntityToAddressDto")
    List<AddressDto> fromEntityListToAddressDtoList(List<Address> addresses);
}
