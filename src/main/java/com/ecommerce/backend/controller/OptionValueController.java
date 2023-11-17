package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.optionValue.OptionValueAdminDto;
import com.ecommerce.backend.dto.optionValue.OptionValueDto;
import com.ecommerce.backend.form.optionValue.CreateOptionValueForm;
import com.ecommerce.backend.form.optionValue.UpdateOptionValueForm;
import com.ecommerce.backend.service.OptionValueService;
import com.ecommerce.backend.storage.criteria.OptionValueCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/option-value")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class OptionValueController {

    @Autowired
    OptionValueService optionValueService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<OptionValueAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<OptionValueAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(optionValueService.getOptionValueById(id));
        apiMessageDto.setMessage("Get option value success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<OptionValueAdminDto>>> getList(OptionValueCriteria optionValueCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<OptionValueAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(optionValueService.getOptionValueList(optionValueCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list option value success");
        return responseListDtoApiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<OptionValueDto>> getListAutoComplete(OptionValueCriteria optionValueCriteria) {
        ApiMessageDto<List<OptionValueDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(optionValueService.getOptionValueListAutoComplete(optionValueCriteria));
        apiMessageDto.setMessage("Get list auto complete success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<OptionValueDto> create(@Valid @RequestBody CreateOptionValueForm createOptionValueForm, BindingResult bindingResult) {
        ApiMessageDto<OptionValueDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(optionValueService.createOptionValue(createOptionValueForm));
        apiMessageDto.setMessage("Create option value success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateOptionValueForm updateOptionValueForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        optionValueService.updateOptionValue(updateOptionValueForm);
        apiMessageDto.setMessage("Update option value success");
        return apiMessageDto;
    }
}
