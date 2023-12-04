package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.address.AddressDto;
import com.ecommerce.backend.form.address.CreateAddressForm;
import com.ecommerce.backend.form.address.UpdateAddressForm;
import com.ecommerce.backend.storage.criteria.AddressCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {

    AddressDto getAddressById(Long id);

    ResponseListDto<List<AddressDto>> getAddressList(Long customerId, AddressCriteria addressCriteria, Pageable pageable);

    void createAddress(Long customerId, CreateAddressForm createAddressForm);

    void updateAddress(Long customerId, UpdateAddressForm updateAddressForm);

    void deleteAddress(Long id, Long customerId);

    void updateDefailt(Long id, Long customerId);
}
