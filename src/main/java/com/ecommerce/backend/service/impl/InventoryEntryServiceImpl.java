package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryAdminDto;
import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryDto;
import com.ecommerce.backend.exception.AlreadyExistsException;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.inventoryEntry.CreateInventoryEntryForm;
import com.ecommerce.backend.form.inventoryEntry.UpdateInventoryEntryForm;
import com.ecommerce.backend.mapper.InventoryEntryMapper;
import com.ecommerce.backend.repository.InventoryEntryRepository;
import com.ecommerce.backend.repository.ProviderRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.InventoryEntryService;
import com.ecommerce.backend.service.OTPService;
import com.ecommerce.backend.storage.criteria.InventoryEntryCriteria;
import com.ecommerce.backend.storage.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryEntryServiceImpl implements InventoryEntryService {

    @Autowired
    InventoryEntryRepository inventoryEntryRepository;

    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InventoryEntryMapper inventoryEntryMapper;

    @Autowired
    OTPService otpService;

    @Override
    public InventoryEntryAdminDto getInventoryEntryById(Long id) {
        InventoryEntry inventoryEntry = inventoryEntryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found inventory entry"));
        return inventoryEntryMapper.fromEntityToInventoryEntryAdminDto(inventoryEntry);
    }

    @Override
    public ResponseListDto<List<InventoryEntryAdminDto>> getInventoryEntryList(InventoryEntryCriteria inventoryEntryCriteria, Pageable pageable) {
        Page<InventoryEntry> inventoryEntries = inventoryEntryRepository.findAll(inventoryEntryCriteria.getCriteria(), pageable);
        ResponseListDto<List<InventoryEntryAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(inventoryEntryMapper.fromEntityListToInventoryEntryAdminDtoList(inventoryEntries.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(inventoryEntries.getTotalPages());
        responseListDto.setTotalElements(inventoryEntries.getTotalElements());
        return responseListDto;
    }

    @Override
    public List<InventoryEntryDto> getInventoryEntryListAutoComplete(InventoryEntryCriteria inventoryEntryCriteria) {
        List<InventoryEntry> inventoryEntries = inventoryEntryRepository.findAll(inventoryEntryCriteria.getCriteria());
        return inventoryEntryMapper.fromEntityListToInventoryEntryDtoAutoCompleteList(inventoryEntries);
    }

    @Override
    public void createInventoryEntry(CreateInventoryEntryForm createInventoryEntryForm, Long userId) {
        Provider provider = providerRepository.findById(createInventoryEntryForm.getProviderId())
                .orElseThrow(() -> new NotFoundException("Not found provider"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user"));

        InventoryEntry inventoryEntryByInvoiceCode = inventoryEntryRepository.findByInvoiceCode(createInventoryEntryForm.getInvoiceCode());
        if(inventoryEntryByInvoiceCode != null){
            throw new AlreadyExistsException("Inventory Entry already exist invoice code");
        }
        InventoryEntry inventoryEntry = inventoryEntryMapper.fromCreateInventoryEntryFormToEntity(createInventoryEntryForm);
        inventoryEntry.setProvider(provider);
        inventoryEntry.setUser(user);
        inventoryEntryRepository.save(inventoryEntry);
    }

    @Override
    public void updateInventoryEntry(UpdateInventoryEntryForm updateInventoryEntryForm) {
        InventoryEntry inventoryEntry = inventoryEntryRepository.findById(updateInventoryEntryForm.getId())
                .orElseThrow(() -> new NotFoundException("Not found inventory entry"));

        if(!inventoryEntry.getInvoiceCode().equals(updateInventoryEntryForm.getInvoiceCode())){
            InventoryEntry inventoryEntryByInvoiceCode = inventoryEntryRepository.findByInvoiceCode(updateInventoryEntryForm.getInvoiceCode());
            if(inventoryEntryByInvoiceCode != null){
                throw new AlreadyExistsException("Inventory Entry already exist invoice code");
            }
        }
        inventoryEntryMapper.fromUpdateInventoryEntryFormToEntity(updateInventoryEntryForm, inventoryEntry);
        inventoryEntryRepository.save(inventoryEntry);
    }

    @Override
    public void deleteInventoryEntry(Long id) {
        InventoryEntry inventoryEntry = inventoryEntryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found inventory entry"));
        inventoryEntryRepository.deleteById(id);
    }
}
