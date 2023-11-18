package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.inventory.InventoryAdminDto;
import com.ecommerce.backend.dto.inventory.InventoryDto;
import com.ecommerce.backend.dto.optionValue.OptionValueAdminDto;
import com.ecommerce.backend.dto.optionValue.OptionValueDto;
import com.ecommerce.backend.dto.pricingStrategy.PricingStrategyDto;
import com.ecommerce.backend.form.inventory.CreateInventoryForm;
import com.ecommerce.backend.form.inventory.UpdateInventoryForm;
import com.ecommerce.backend.form.optionValue.CreateOptionValueForm;
import com.ecommerce.backend.form.optionValue.UpdateOptionValueForm;
import com.ecommerce.backend.service.InventoryService;
import com.ecommerce.backend.storage.criteria.InventoryCriteria;
import com.ecommerce.backend.storage.criteria.OptionValueCriteria;
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
@RequestMapping("/v1/inventory")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<InventoryAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<InventoryAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(inventoryService.getInventoryById(id));
        apiMessageDto.setMessage("Get inventory success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<InventoryAdminDto>>> getList(InventoryCriteria inventoryCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<InventoryAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(inventoryService.getInventoryList(inventoryCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list inventory success");
        return responseListDtoApiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<InventoryDto>> getListAutoComplete(InventoryCriteria inventoryCriteria) {
        ApiMessageDto<List<InventoryDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(inventoryService.getInventoryListAutoComplete(inventoryCriteria));
        apiMessageDto.setMessage("Get list auto complete success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateInventoryForm createInventoryForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        inventoryService.createInventory(createInventoryForm);
        apiMessageDto.setMessage("Create inventory success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateInventoryForm updateInventoryForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        inventoryService.updateInventory(updateInventoryForm);
        apiMessageDto.setMessage("Update inventory success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        inventoryService.deleteInventory(id);
        apiMessageDto.setMessage("Delete inventory success");
        return apiMessageDto;
    }
}
