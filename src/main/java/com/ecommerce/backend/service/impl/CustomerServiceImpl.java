package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.customer.CustomerAdminDto;
import com.ecommerce.backend.dto.customer.CustomerDto;
import com.ecommerce.backend.dto.pricingStrategy.PricingStrategyAdminDto;
import com.ecommerce.backend.exception.AlreadyExistsException;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.customer.RegisterCustomerForm;
import com.ecommerce.backend.form.customer.UpdateCustomerForm;
import com.ecommerce.backend.mapper.CustomerMapper;
import com.ecommerce.backend.repository.CustomerRepository;
import com.ecommerce.backend.service.CustomerService;
import com.ecommerce.backend.storage.criteria.CustomerCriteria;
import com.ecommerce.backend.storage.entity.Customer;
import com.ecommerce.backend.storage.entity.PricingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public CustomerAdminDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        return customerMapper.fromEntityToCustomerAdminDto(customer);
    }

    @Override
    public ResponseListDto<List<CustomerAdminDto>> getCustomerList(CustomerCriteria CustomerCriteria, Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(CustomerCriteria.getCriteria(), pageable);
        ResponseListDto<List<CustomerAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(customerMapper.fromEntityListToCustomerAdminDtoList(customers.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(customers.getTotalPages());
        responseListDto.setTotalElements(customers.getTotalElements());
        return responseListDto;
    }

    @Override
    public List<CustomerDto> getCustomerListAutoComplete(CustomerCriteria CustomerCriteria) {
        List<Customer> customers = customerRepository.findAll(CustomerCriteria.getCriteria());
        return customerMapper.fromEntityListToCustomerDtoAutoCompleteList(customers);
    }

    @Override
    public void updateCustomer(UpdateCustomerForm updateCustomerForm) {
        Customer customer = customerRepository.findById(updateCustomerForm.getId()).orElse(null);
        if(customer == null){
            throw new NotFoundException("Not found customer");
        }
        if(!customer.getPhone().equals(updateCustomerForm.getPhone())){
            Customer customerByPhone = customerRepository.findByPhone(updateCustomerForm.getPhone());
            if(customerByPhone != null){
                throw new AlreadyExistsException("Phone already exist");
            }
        }
        if(!customer.getEmail().equals(updateCustomerForm.getEmail())){
            Customer customerByEmail = customerRepository.findByEmail(updateCustomerForm.getEmail());
            if(customerByEmail != null){
                throw new AlreadyExistsException("Email already exist");
            }
        }
        if (StringUtils.isNoneBlank(updateCustomerForm.getPassword())) {
            customer.setPassword(passwordEncoder.encode(updateCustomerForm.getPassword()));
        }
        customerMapper.fromUpdateCustomerFormToEntity(updateCustomerForm, customer);
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        customerRepository.deleteById(id);
    }

    @Override
    public void registerCustomer(RegisterCustomerForm registerCustomerForm) {
        Customer customerByUsername = customerRepository.findByUsername(registerCustomerForm.getUsername());
        if(customerByUsername != null){
            throw new AlreadyExistsException("Username already exist");
        }
        Customer customerByPhone = customerRepository.findByPhone(registerCustomerForm.getPhone());
        if(customerByPhone != null){
            throw new AlreadyExistsException("Phone already exist");
        }
        Customer customerByEmail = customerRepository.findByEmail(registerCustomerForm.getEmail());
        if(customerByEmail != null){
            throw new AlreadyExistsException("Email already exist");
        }
        Customer customer = customerMapper.fromRegisterCustomerFormToEntity(registerCustomerForm);
        customer.setPassword(passwordEncoder.encode(registerCustomerForm.getPassword()));
        customerRepository.save(customer);
    }
}
