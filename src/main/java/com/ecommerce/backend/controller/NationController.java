package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.nation.NationAdminDto;
import com.ecommerce.backend.dto.nation.NationDto;
import com.ecommerce.backend.form.nation.CreateNationForm;
import com.ecommerce.backend.form.nation.UpdateNationForm;
import com.ecommerce.backend.service.NationService;
import com.ecommerce.backend.storage.criteria.NationCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/nation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class NationController {

    @Autowired
    NationService nationService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<NationAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<NationAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(nationService.getNationById(id));
        apiMessageDto.setMessage("Get nation success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<NationAdminDto>>> getList(NationCriteria nationCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<NationAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(nationService.getNationList(nationCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list nation success");
        return responseListDtoApiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<NationDto>> getListAutoComplete(NationCriteria nationCriteria) {
        ApiMessageDto<List<NationDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(nationService.getNationListAutoComplete(nationCriteria));
        apiMessageDto.setMessage("Get list auto complete success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateNationForm createNationForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        nationService.createNation(createNationForm);
        apiMessageDto.setMessage("Create nation success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateNationForm updateNationForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        nationService.updateNation(updateNationForm);
        apiMessageDto.setMessage("Update nation success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        nationService.deleteNation(id);
        apiMessageDto.setMessage("Delete nation success");
        return apiMessageDto;
    }
}
