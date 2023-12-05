package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.address.AddressDto;
import com.ecommerce.backend.form.address.CreateAddressForm;
import com.ecommerce.backend.form.address.UpdateAddressForm;
import com.ecommerce.backend.service.AddressService;
import com.ecommerce.backend.storage.criteria.AddressCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/address")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class AddressController extends BasicController{

    @Autowired
    private AddressService addressService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<AddressDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<AddressDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(addressService.getAddressById(id));
        apiMessageDto.setMessage("Get address success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<AddressDto>>> getList(AddressCriteria addressCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<AddressDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(addressService.getAddressList(getCurrentUser(), addressCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list address success");
        return responseListDtoApiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<AddressDto> create(@Valid @RequestBody CreateAddressForm createAddressForm, BindingResult bindingResult) {
        ApiMessageDto<AddressDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(addressService.createAddress(getCurrentUser(), createAddressForm));
        apiMessageDto.setMessage("Create address success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateAddressForm updateAddressForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        addressService.updateAddress(getCurrentUser(), updateAddressForm);
        apiMessageDto.setMessage("Update address success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        addressService.deleteAddress(id, getCurrentUser());
        apiMessageDto.setMessage("Delete nation success");
        return apiMessageDto;
    }

    @GetMapping(value = "/update-default", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateDefault(@RequestParam("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        addressService.updateDefailt(id, getCurrentUser());
        apiMessageDto.setMessage("Update default address success");
        return apiMessageDto;
    }
}
