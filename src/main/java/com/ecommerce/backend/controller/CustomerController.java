package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.account.LoginAuthDto;
import com.ecommerce.backend.dto.customer.CustomerAdminDto;
import com.ecommerce.backend.dto.customer.CustomerDto;
import com.ecommerce.backend.dto.option.OptionDto;
import com.ecommerce.backend.dto.user.UserAdminDto;
import com.ecommerce.backend.form.customer.LoginCustomerForm;
import com.ecommerce.backend.form.customer.RegisterCustomerForm;
import com.ecommerce.backend.form.customer.UpdateCustomerForm;
import com.ecommerce.backend.form.inventoryEntryDetail.CreateInventoryEntryDetailForm;
import com.ecommerce.backend.form.option.UpdateOptionForm;
import com.ecommerce.backend.service.CustomerService;
import com.ecommerce.backend.storage.criteria.CustomerCriteria;
import com.ecommerce.backend.storage.criteria.OptionCriteria;
import com.ecommerce.backend.storage.criteria.UserCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/customer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CustomerController extends BasicController{

    @Autowired
    CustomerService customerService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CustomerAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<CustomerAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(customerService.getCustomerById(id));
        apiMessageDto.setMessage("Get customer success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<CustomerAdminDto>>> getList(CustomerCriteria customerCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<CustomerAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(customerService.getCustomerList(customerCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list customer success");
        return responseListDtoApiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<CustomerDto>> getListAutoComplete(CustomerCriteria customerCriteria) {
        ApiMessageDto<List<CustomerDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(customerService.getCustomerListAutoComplete(customerCriteria));
        apiMessageDto.setMessage("Get list auto complete success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateCustomerForm updateCustomerForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        customerService.updateCustomer(updateCustomerForm);
        apiMessageDto.setMessage("Update customer success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        customerService.deleteCustomer(id);
        apiMessageDto.setMessage("Delete customer success");
        return apiMessageDto;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> register(@Valid @RequestBody RegisterCustomerForm registerCustomerForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        customerService.registerCustomer(registerCustomerForm);
        apiMessageDto.setMessage("Register customer success");
        return apiMessageDto;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<LoginAuthDto> login(@Valid @RequestBody LoginCustomerForm loginCustomerForm, BindingResult bindingResult) {
        ApiMessageDto<LoginAuthDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(customerService.loginCustomer(loginCustomerForm));
        apiMessageDto.setMessage("Login success");
        return apiMessageDto;
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CustomerDto> profile() {
        ApiMessageDto<CustomerDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(customerService.profileCustomer(getCurrentUser()));
        apiMessageDto.setMessage("Get profile success");
        return apiMessageDto;
    }
}
