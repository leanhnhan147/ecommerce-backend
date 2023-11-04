package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.mediaResource.MediaResourceDto;
import com.ecommerce.backend.storage.entity.MediaResource;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MediaResourceMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "url", target = "url")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToMediaResourceDto")
    MediaResourceDto fromEntityToMediaResourceDto(MediaResource mediaResource);
}