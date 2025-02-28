package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.option.OptionAdminDto;
import com.ecommerce.backend.dto.option.OptionDto;
import com.ecommerce.backend.form.option.CreateOptionForm;
import com.ecommerce.backend.form.option.UpdateOptionForm;
import com.ecommerce.backend.service.OptionService;
import com.ecommerce.backend.storage.criteria.OptionCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/option")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class OptionController {

    @Autowired
    OptionService optionService;

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<OptionAdminDto> get(@RequestParam(value = "id", required = false) Long id,
                                             @RequestParam(value = "code", required = false) String code) {
        ApiMessageDto<OptionAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(optionService.getOptionByIdOrCode(id, code));
        apiMessageDto.setMessage("Get option success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<OptionAdminDto>>> getList(OptionCriteria optionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<OptionAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(optionService.getOptionList(optionCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list option success");
        return responseListDtoApiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<OptionDto>> getListAutoComplete(OptionCriteria optionCriteria) {
        ApiMessageDto<List<OptionDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(optionService.getOptionListAutoComplete(optionCriteria));
        apiMessageDto.setMessage("Get list auto complete success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<OptionDto> create(@Valid @RequestBody CreateOptionForm createOptionForm, BindingResult bindingResult) {
        ApiMessageDto<OptionDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(optionService.createOption(createOptionForm));
        apiMessageDto.setMessage("Create option success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateOptionForm updateOptionForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        optionService.updateOption(updateOptionForm);
        apiMessageDto.setMessage("Update option success");
        return apiMessageDto;
    }
}
