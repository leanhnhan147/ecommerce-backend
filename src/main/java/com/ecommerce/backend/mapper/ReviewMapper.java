package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.review.ReviewDto;
import com.ecommerce.backend.form.review.CreateReviewForm;
import com.ecommerce.backend.storage.entity.Review;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CustomerMapper.class, ProductVariationMapper.class})
public interface ReviewMapper {

    @Mapping(source = "content", target = "content")
    @Mapping(source = "point", target = "point")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    Review fromCreateNationFormToEntity(CreateReviewForm createReviewForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "point", target = "point")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "customer", target = "customer", qualifiedByName = "fromEntityToCustomerDtoAutoComplete")
    @Mapping(source = "productVariation", target = "productVariation", qualifiedByName = "fromEntityToProductVariationDtoForReview")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToReviewDto")
    ReviewDto fromEntityToReviewDto(Review review);

    @IterableMapping(elementTargetType = ReviewDto.class, qualifiedByName = "fromEntityToReviewDto")
    List<ReviewDto> fromEntityListToReviewDtoList(List<Review> reviews);
}
