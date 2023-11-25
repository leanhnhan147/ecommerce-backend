package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.provider.ProviderAdminDto;
import com.ecommerce.backend.dto.provider.ProviderDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.provider.CreateProviderForm;
import com.ecommerce.backend.form.provider.UpdateProviderForm;
import com.ecommerce.backend.mapper.ProviderMapper;
import com.ecommerce.backend.repository.ProviderRepository;
import com.ecommerce.backend.service.ProviderService;
import com.ecommerce.backend.storage.criteria.ProviderCriteria;
import com.ecommerce.backend.storage.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    ProviderMapper providerMapper;

    @Override
    public ProviderAdminDto getProvicderById(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found provider"));
        return providerMapper.fromEntityToProviderAdminDto(provider);
    }

    @Override
    public ResponseListDto<List<ProviderAdminDto>> getProvicderList(ProviderCriteria providerCriteria, Pageable pageable) {
        Page<Provider> providers = providerRepository.findAll(providerCriteria.getCriteria(), pageable);
        ResponseListDto<List<ProviderAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(providerMapper.fromEntityListToProviderAdminDtoList(providers.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(providers.getTotalPages());
        responseListDto.setTotalElements(providers.getTotalElements());
        return responseListDto;
    }

    @Override
    public List<ProviderDto> getProvicderListAutoComplete(ProviderCriteria providerCriteria) {
        List<Provider> providers = providerRepository.findAll(providerCriteria.getCriteria());
        return providerMapper.fromEntityListToProviderDtoAutoCompleteList(providers);
    }

    @Override
    public void createProvicder(CreateProviderForm providerForm) {
        Provider provider = providerMapper.fromCreateProviderFormToEntity(providerForm);
        providerRepository.save(provider);
    }

    @Override
    public void updateProvicder(UpdateProviderForm updateProviderForm) {
        Provider provider = providerRepository.findById(updateProviderForm.getId())
                .orElseThrow(() -> new NotFoundException("Not found provider"));
        providerMapper.fromUpdateProviderFormToEntity(updateProviderForm, provider);
        providerRepository.save(provider);
    }

    @Override
    public void deleteProvicder(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found provider"));
        providerRepository.deleteById(id);
    }
}
