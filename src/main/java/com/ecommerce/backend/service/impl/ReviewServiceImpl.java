package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.review.ReviewDto;
import com.ecommerce.backend.exception.AlreadyExistsException;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.review.CreateReviewForm;
import com.ecommerce.backend.form.review.UpdateStateReviewForm;
import com.ecommerce.backend.mapper.ReviewMapper;
import com.ecommerce.backend.repository.*;
import com.ecommerce.backend.service.ReviewService;
import com.ecommerce.backend.storage.criteria.ReviewCriteria;
import com.ecommerce.backend.storage.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MediaResourceRepository mediaResourceRepository;

    @Autowired
    private OptionValueRepository optionValueRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found review"));
        ReviewDto reviewDto = reviewMapper.fromEntityToReviewDto(review);
        List<ReviewImage> reviewImages = reviewImageRepository.findByReviewId(review.getId());
        reviewDto.setImages(getImages(reviewImages));
        List<OptionValue> optionValues = optionValueRepository.findByProductVariationId(reviewDto.getProductVariation().getId());
        List<String> optionValueNames = new ArrayList<>();
        for (OptionValue optionValue : optionValues){
            optionValueNames.add(optionValue.getDisplayName());
        }
        reviewDto.getProductVariation().setOptionValues(optionValueNames);
        return reviewDto;
    }

    @Override
    public ResponseListDto<List<ReviewDto>> getList(ReviewCriteria reviewCriteria, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAll(reviewCriteria.getCriteria(), pageable);
        ResponseListDto<List<ReviewDto>> responseListDto = new ResponseListDto<>();
        List<ReviewDto> reviewDtos = reviewMapper.fromEntityListToReviewDtoList(reviews.getContent());
        for (ReviewDto reviewDto : reviewDtos){
            List<ReviewImage> reviewImages = reviewImageRepository.findByReviewId(reviewDto.getId());
            reviewDto.setImages(getImages(reviewImages));
        }
        responseListDto.setContent(reviewDtos);
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(reviews.getTotalPages());
        responseListDto.setTotalElements(reviews.getTotalElements());
        return responseListDto;
    }

    @Override
    public ResponseListDto<List<ReviewDto>> getListReview(ReviewCriteria reviewCriteria, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdDate").descending());
        Page<Review> reviews = reviewRepository.findAll(reviewCriteria.getCriteria(), pageable);
        ResponseListDto<List<ReviewDto>> responseListDto = new ResponseListDto<>();
        List<ReviewDto> reviewDtos = reviewMapper.fromEntityListToReviewDtoList(reviews.getContent());
        for (ReviewDto reviewDto : reviewDtos){
            List<ReviewImage> reviewImages = reviewImageRepository.findByReviewId(reviewDto.getId());
            reviewDto.setImages(getImages(reviewImages));
        }
        responseListDto.setContent(reviewDtos);
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(reviews.getTotalPages());
        responseListDto.setTotalElements(reviews.getTotalElements());
        return responseListDto;
    }

    private List<String> getImages(List<ReviewImage> reviewImages){
        List<String> images = new ArrayList<>();
        for(ReviewImage reviewImage : reviewImages){
            images.add(reviewImage.getMediaResource().getUrl());
        }
        return images;
    }

    @Override
    public void createReview(CreateReviewForm createReviewForm, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        ProductVariation productVariation = productVariationRepository.findById(createReviewForm.getProductVariationId())
                .orElseThrow(() -> new NotFoundException("Not found product variation"));
        Order order = orderRepository.findByIdAndState(createReviewForm.getOrderId(), Constant.ORDER_STATE_DELIVERED)
                .orElseThrow(() -> new NotFoundException("Not found order has state DELIVERED"));
        Review review = reviewRepository.findByCustomerIdAndProductVariationId(customer.getId(), productVariation.getId()).orElse(null);
        if(review != null){
            throw new AlreadyExistsException("Customer has reviewed this product variation");
        }
        review = reviewMapper.fromCreateNationFormToEntity(createReviewForm);
        review.setState(Constant.REVIEW_STATE_PENDING);
        review.setCreatedDate(new Date());
        review.setCustomer(customer);
        review.setProductVariation(productVariation);
        review.setOrder(order);
        reviewRepository.save(review);
        for (int i = 0; i < createReviewForm.getImageIds().length; i++){
            MediaResource mediaResource = mediaResourceRepository.findById(createReviewForm.getImageIds()[i])
                    .orElseThrow(() -> new NotFoundException("Not found media resource"));
            reviewImageRepository.save(new ReviewImage(review, mediaResource));
        }
    }

    @Override
    public void updateStateReview(UpdateStateReviewForm updateStateReviewForm) {
        Review review = reviewRepository.findById(updateStateReviewForm.getId())
                .orElseThrow(() -> new NotFoundException("Not found review"));
        review.setState(updateStateReviewForm.getState());
        reviewRepository.save(review);
    }
}
