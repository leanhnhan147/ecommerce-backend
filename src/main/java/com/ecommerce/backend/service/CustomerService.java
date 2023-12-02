package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.account.LoginAuthDto;
import com.ecommerce.backend.dto.customer.CustomerAdminDto;
import com.ecommerce.backend.dto.customer.CustomerDto;
import com.ecommerce.backend.form.customer.LoginCustomerForm;
import com.ecommerce.backend.form.customer.RegisterCustomerForm;
import com.ecommerce.backend.form.customer.UpdateCustomerForm;
import com.ecommerce.backend.form.customer.UpdateProfileCustomerForm;
import com.ecommerce.backend.storage.criteria.CustomerCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    CustomerAdminDto getCustomerById(Long id);

    ResponseListDto<List<CustomerAdminDto>> getCustomerList(CustomerCriteria CustomerCriteria, Pageable pageable);

    List<CustomerDto> getCustomerListAutoComplete(CustomerCriteria CustomerCriteria);

    void updateCustomer(UpdateCustomerForm updateCustomerForm);

    void deleteCustomer(Long id);

    void registerCustomer(RegisterCustomerForm registerCustomerForm);

    CustomerDto loginCustomer(LoginCustomerForm loginCustomerForm);

    CustomerDto profileCustomer(Long id);

    void updateProfileCustomer(Long id, UpdateProfileCustomerForm updateProfileCustomerForm);
}
