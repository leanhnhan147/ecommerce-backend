package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.inventoryEntryDetail.InventoryEntryDetailAdminDto;
import com.ecommerce.backend.dto.inventoryEntryDetail.InventoryEntryDetailDto;
import com.ecommerce.backend.form.inventoryEntryDetail.CreateInventoryEntryDetailForm;
import com.ecommerce.backend.form.inventoryEntryDetail.UpdateInventoryEntryDetailForm;
import com.ecommerce.backend.service.InventoryEntryDetailService;
import com.ecommerce.backend.storage.criteria.InventoryEntryDetailCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/inventory-entry-detail")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class InventoryEntryDetailController {

    @Autowired
    private InventoryEntryDetailService inventoryEntryDetailService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<InventoryEntryDetailAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<InventoryEntryDetailAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(inventoryEntryDetailService.getInventoryEntryDetailById(id));
        apiMessageDto.setMessage("Get inventory entry detail success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<InventoryEntryDetailAdminDto>>> getList(InventoryEntryDetailCriteria inventoryEntryDetailCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<InventoryEntryDetailAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(inventoryEntryDetailService.getInventoryEntryDetailList(inventoryEntryDetailCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list inventory entry detail success");
        return responseListDtoApiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<InventoryEntryDetailDto>> getListAutoComplete(InventoryEntryDetailCriteria inventoryEntryDetailCriteria) {
        ApiMessageDto<List<InventoryEntryDetailDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(inventoryEntryDetailService.getInventoryEntryDetailListAutoComplete(inventoryEntryDetailCriteria));
        apiMessageDto.setMessage("Get list auto complete success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateInventoryEntryDetailForm createInventoryEntryDetailForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        inventoryEntryDetailService.createInventoryEntryDetail(createInventoryEntryDetailForm);
        apiMessageDto.setMessage("Create inventory entry detail success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateInventoryEntryDetailForm updateInventoryEntryDetailForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        inventoryEntryDetailService.updateInventoryEntryDetail(updateInventoryEntryDetailForm);
        apiMessageDto.setMessage("Update inventory entry detail success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        inventoryEntryDetailService.deleteInventoryEntryDetail(id);
        apiMessageDto.setMessage("Delete inventory entry detail success");
        return apiMessageDto;
    }
}
