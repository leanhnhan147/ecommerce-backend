package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.nation.NationAdminDto;
import com.ecommerce.backend.dto.review.ReviewDto;
import com.ecommerce.backend.form.nation.CreateNationForm;
import com.ecommerce.backend.form.nation.UpdateNationForm;
import com.ecommerce.backend.form.review.CreateReviewForm;
import com.ecommerce.backend.form.review.UpdateStateReviewForm;
import com.ecommerce.backend.service.ReviewService;
import com.ecommerce.backend.storage.criteria.NationCriteria;
import com.ecommerce.backend.storage.criteria.ReviewCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/review")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ReviewController extends BasicController{

    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ReviewDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<ReviewDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(reviewService.getReviewById(id));
        apiMessageDto.setMessage("Get review success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<ReviewDto>>> getList(ReviewCriteria reviewCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<ReviewDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(reviewService.getReviewList(reviewCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list review success");
        return responseListDtoApiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateReviewForm createReviewForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        reviewService.createReview(createReviewForm, getCurrentUser());
        apiMessageDto.setMessage("Create review success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-state", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateState(@Valid @RequestBody UpdateStateReviewForm updateStateReviewForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        reviewService.updateStateReview(updateStateReviewForm);
        apiMessageDto.setMessage("Update state review success");
        return apiMessageDto;
    }
}
