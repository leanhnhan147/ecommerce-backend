package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.provider.ProviderAdminDto;
import com.ecommerce.backend.dto.provider.ProviderDto;
import com.ecommerce.backend.form.provider.CreateProviderForm;
import com.ecommerce.backend.form.provider.UpdateProviderForm;
import com.ecommerce.backend.service.ProviderService;
import com.ecommerce.backend.storage.criteria.ProviderCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/provider")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ProviderController {

    @Autowired
    ProviderService providerService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProviderAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<ProviderAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(providerService.getProvicderById(id));
        apiMessageDto.setMessage("Get provider success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<ProviderAdminDto>>> getList(ProviderCriteria providerCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<ProviderAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(providerService.getProvicderList(providerCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list provider success");
        return responseListDtoApiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<ProviderDto>> getListAutoComplete(ProviderCriteria providerCriteria) {
        ApiMessageDto<List<ProviderDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(providerService.getProvicderListAutoComplete(providerCriteria));
        apiMessageDto.setMessage("Get list auto complete success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateProviderForm createProviderForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        providerService.createProvicder(createProviderForm);
        apiMessageDto.setMessage("Create provider success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateProviderForm updateProviderForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        providerService.updateProvicder(updateProviderForm);
        apiMessageDto.setMessage("Update provider success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        providerService.deleteProvicder(id);
        apiMessageDto.setMessage("Delete provider success");
        return apiMessageDto;
    }
}
