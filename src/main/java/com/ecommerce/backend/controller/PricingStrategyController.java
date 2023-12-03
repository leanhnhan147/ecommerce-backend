package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.pricingStrategy.PricingStrategyAdminDto;
import com.ecommerce.backend.dto.pricingStrategy.PricingStrategyDto;
import com.ecommerce.backend.form.pricingStrategy.CreatePricingStrategyForm;
import com.ecommerce.backend.form.pricingStrategy.UpdatePricingStrategyForm;
import com.ecommerce.backend.service.PricingStrategyService;
import com.ecommerce.backend.storage.criteria.PricingStrategyCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/pricing-strategy")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class PricingStrategyController extends BasicController {

    @Autowired
    PricingStrategyService pricingStrategyService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<PricingStrategyAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<PricingStrategyAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(pricingStrategyService.getPricingStrategyById(id));
        apiMessageDto.setMessage("Get pricing strategy success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<PricingStrategyAdminDto>>> getList(PricingStrategyCriteria pricingStrategyCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<PricingStrategyAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(pricingStrategyService.getPricingStrategyList(pricingStrategyCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list pricing strategy success");
        return responseListDtoApiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<PricingStrategyDto>> getListAutoComplete(PricingStrategyCriteria pricingStrategyCriteria) {
        ApiMessageDto<List<PricingStrategyDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(pricingStrategyService.getPricingStrategyListAutoComplete(pricingStrategyCriteria));
        apiMessageDto.setMessage("Get list auto complete success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreatePricingStrategyForm pricingStrategyForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        pricingStrategyService.createPricingStrategy(pricingStrategyForm, getCurrentUser());
        apiMessageDto.setMessage("Create pricing strategy success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdatePricingStrategyForm updatePricingStrategyForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        pricingStrategyService.updatePricingStrategy(updatePricingStrategyForm);
        apiMessageDto.setMessage("Update pricing strategy success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        pricingStrategyService.deletePricingStrategy(id);
        apiMessageDto.setMessage("Delete pricing strategy success");
        return apiMessageDto;
    }
}
