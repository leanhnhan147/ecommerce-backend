package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.nation.NationAdminDto;
import com.ecommerce.backend.dto.nation.NationDto;
import com.ecommerce.backend.form.nation.CreateNationForm;
import com.ecommerce.backend.form.nation.UpdateNationForm;
import com.ecommerce.backend.storage.entity.Nation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NationMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "postCode", target = "postCode")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    Nation fromCreateNationFormToEntity(CreateNationForm createNationForm);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "postCode", target = "postCode")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateNationFormToEntity(UpdateNationForm updateNationForm, @MappingTarget Nation nation);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "postCode", target = "postCode")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "parent", target = "parent", qualifiedByName = "fromEntityToNationDtoAutoComplete")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    NationAdminDto fromEntityToNationAdminDto(Nation nation);

    @IterableMapping(elementTargetType = NationAdminDto.class, qualifiedByName = "adminGetMapping")
    List<NationAdminDto> fromEntityListToNationAdminDtoList(List<Nation> nations);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "postCode", target = "postCode")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToNationDtoAutoComplete")
    NationDto fromEntityToNationDtoAutoComplete(Nation nation);

    @IterableMapping(elementTargetType = NationDto.class, qualifiedByName = "fromEntityToNationDtoAutoComplete")
    List<NationDto> fromEntityListToNationDtoAutoCompleteList(List<Nation> nations);
}
