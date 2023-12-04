package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryAdminDto;
import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryDto;
import com.ecommerce.backend.form.inventoryEntry.CreateInventoryEntryForm;
import com.ecommerce.backend.form.inventoryEntry.UpdateInventoryEntryForm;
import com.ecommerce.backend.service.InventoryEntryService;
import com.ecommerce.backend.storage.criteria.InventoryEntryCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/inventory-entry")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class InventoryEntryController extends BasicController {

    @Autowired
    InventoryEntryService inventoryEntryService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<InventoryEntryAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<InventoryEntryAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(inventoryEntryService.getInventoryEntryById(id));
        apiMessageDto.setMessage("Get inventory entry success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<InventoryEntryAdminDto>>> getList(InventoryEntryCriteria inventoryEntryCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<InventoryEntryAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(inventoryEntryService.getInventoryEntryList(inventoryEntryCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list inventory entry success");
        return responseListDtoApiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<InventoryEntryDto>> getListAutoComplete(InventoryEntryCriteria inventoryEntryCriteria) {
        ApiMessageDto<List<InventoryEntryDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(inventoryEntryService.getInventoryEntryListAutoComplete(inventoryEntryCriteria));
        apiMessageDto.setMessage("Get list auto complete success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateInventoryEntryForm createInventoryEntryForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        inventoryEntryService.createInventoryEntry(createInventoryEntryForm, getCurrentUser());
        apiMessageDto.setMessage("Create inventory entry success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateInventoryEntryForm updateInventoryEntryForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        inventoryEntryService.updateInventoryEntry(updateInventoryEntryForm);
        apiMessageDto.setMessage("Update inventory entry success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        inventoryEntryService.deleteInventoryEntry(id);
        apiMessageDto.setMessage("Delete inventory entry success");
        return apiMessageDto;
    }
}
