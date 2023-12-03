package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.address.AddressDto;
import com.ecommerce.backend.exception.BadRequestException;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.address.CreateAddressForm;
import com.ecommerce.backend.form.address.UpdateAddressForm;
import com.ecommerce.backend.mapper.AddressMapper;
import com.ecommerce.backend.repository.AddressRepository;
import com.ecommerce.backend.repository.CustomerRepository;
import com.ecommerce.backend.repository.NationRepository;
import com.ecommerce.backend.service.AddressService;
import com.ecommerce.backend.storage.criteria.AddressCriteria;
import com.ecommerce.backend.storage.entity.Address;
import com.ecommerce.backend.storage.entity.Customer;
import com.ecommerce.backend.storage.entity.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private NationRepository nationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public AddressDto getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found address"));
        return addressMapper.fromEntityToAddressDto(address);
    }

    @Override
    public ResponseListDto<List<AddressDto>> getAddressList(Long customerId, AddressCriteria addressCriteria, Pageable pageable) {
        addressCriteria.setCustomerId(customerId);
        Page<Address> addresses = addressRepository.findAll(addressCriteria.getCriteria(), pageable);
        for (Address address : addresses.getContent()){
            address.setAddressDetail(getAddressDefault(address));
        }
        ResponseListDto<List<AddressDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(addressMapper.fromEntityListToAddressDtoList(addresses.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(addresses.getTotalPages());
        responseListDto.setTotalElements(addresses.getTotalElements());
        return responseListDto;
    }

    private String getAddressDefault(Address address){
        return address.getAddressDetail() + ", " + address.getWard().getName() + ", " + address.getDistrict().getName() + ", " + address.getProvince().getName();
    }

    @Transactional
    @Override
    public void createAddress(Long customerId, CreateAddressForm createAddressForm) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        Nation province = nationRepository.findByIdAndKind(createAddressForm.getProvinceId(), Constant.NATION_KIND_PROVINCE)
                .orElseThrow(() -> new NotFoundException("Not found province"));
        Nation district = nationRepository.findByIdAndKind(createAddressForm.getDistrictId(), Constant.NATION_KIND_DISTRICT)
                .orElseThrow(() -> new NotFoundException("Not found district"));
        if(!district.getParent().getId().equals(province.getId())){
            throw new BadRequestException("Province not has this district");
        }
        Nation ward = nationRepository.findByIdAndKind(createAddressForm.getWardId(), Constant.NATION_KIND_WARD)
                .orElseThrow(() -> new NotFoundException("Not found ward"));
        if(!ward.getParent().getId().equals(district.getId())){
            throw new BadRequestException("district not has this ward");
        }
        Address address = addressMapper.fromCreateAddressFormToEntity(createAddressForm);
        address.setIsDefault(Constant.ADDRESS_NOT_DEFAULT);
        address.setProvince(province);
        address.setDistrict(district);
        address.setWard(ward);
        address.setCustomer(customer);
        addressRepository.save(address);
    }

    @Transactional
    @Override
    public void updateAddress(Long customerId, UpdateAddressForm updateAddressForm) {
        Address address = addressRepository.findById(updateAddressForm.getId())
                .orElseThrow(() -> new NotFoundException("Not found address"));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        if (!address.getCustomer().getId().equals(customer.getId())){
            throw new BadRequestException("Customer not has this address");
        }
        Nation province = nationRepository.findByIdAndKind(updateAddressForm.getProvinceId(), Constant.NATION_KIND_PROVINCE)
                .orElseThrow(() -> new NotFoundException("Not found province"));
        address.setProvince(province);
        Nation district = nationRepository.findByIdAndKind(updateAddressForm.getDistrictId(), Constant.NATION_KIND_DISTRICT)
                .orElseThrow(() -> new NotFoundException("Not found district"));
        if (!district.getParent().getId().equals(province.getId())){
            throw new BadRequestException("Province not has this district");
        }
        address.setDistrict(district);
        Nation ward = nationRepository.findByIdAndKind(updateAddressForm.getWardId(), Constant.NATION_KIND_WARD)
                .orElseThrow(() -> new NotFoundException("Not found ward"));
        if(!ward.getParent().getId().equals(district.getId())){
            throw new BadRequestException("district not has this ward");
        }
        address.setWard(ward);
        addressMapper.fromUpdateAddressFormToEntity(updateAddressForm, address);
        addressRepository.save(address);
    }

    @Transactional
    @Override
    public void deleteAddress(Long id, Long customerId) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found address"));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        if(!address.getCustomer().getId().equals(customer.getId())){
            throw new BadRequestException("Customer not has this address");
        }
        addressRepository.deleteById(id);
    }

    @Override
    public void updateDefailt(Long id, Long customerId) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found address"));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        if(!address.getCustomer().getId().equals(customer.getId())){
            throw new BadRequestException("Customer not has this address");
        }
        Address addressIsDefault = addressRepository.findFirstByCustomerIdAndIsDefault(customer.getId(), Constant.ADDRESS_DEFAULT);
        if(addressIsDefault != null){
            addressIsDefault.setIsDefault(Constant.ADDRESS_NOT_DEFAULT);
            addressRepository.save(addressIsDefault);
        }
        address.setIsDefault(Constant.ADDRESS_DEFAULT);
        addressRepository.save(address);
    }
}
