package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryAdminDto;
import com.ecommerce.backend.dto.inventoryEntryDetail.InventoryEntryDetailAdminDto;
import com.ecommerce.backend.dto.inventoryEntryDetail.InventoryEntryDetailDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.inventoryEntryDetail.CreateInventoryEntryDetailForm;
import com.ecommerce.backend.form.inventoryEntryDetail.UpdateInventoryEntryDetailForm;
import com.ecommerce.backend.mapper.InventoryEntryDetailMapper;
import com.ecommerce.backend.repository.InventoryEntryDetailRepository;
import com.ecommerce.backend.repository.InventoryEntryRepository;
import com.ecommerce.backend.repository.ProductVariationRepository;
import com.ecommerce.backend.service.InventoryEntryDetailService;
import com.ecommerce.backend.storage.criteria.InventoryEntryDetailCriteria;
import com.ecommerce.backend.storage.entity.InventoryEntry;
import com.ecommerce.backend.storage.entity.InventoryEntryDetail;
import com.ecommerce.backend.storage.entity.ProductVariation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryEntryDetailServiceImpl implements InventoryEntryDetailService {

    @Autowired
    private InventoryEntryDetailRepository inventoryEntryDetailRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private InventoryEntryRepository inventoryEntryRepository;

    @Autowired
    private InventoryEntryDetailMapper inventoryEntryDetailMapper;

    @Override
    public InventoryEntryDetailAdminDto getInventoryEntryDetailById(Long id) {
        InventoryEntryDetail inventoryEntryDetail = inventoryEntryDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found inventory entry detail"));
        return inventoryEntryDetailMapper.fromEntityToInventoryEntryDetailAdminDto(inventoryEntryDetail);
    }

    @Override
    public ResponseListDto<List<InventoryEntryDetailAdminDto>> getInventoryEntryDetailList(InventoryEntryDetailCriteria inventoryEntryDetailCriteria, Pageable pageable) {
        Page<InventoryEntryDetail> inventoryEntryDetails = inventoryEntryDetailRepository.findAll(inventoryEntryDetailCriteria.getCriteria(), pageable);
        ResponseListDto<List<InventoryEntryDetailAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(inventoryEntryDetailMapper.fromEntityListToInventoryEntryDetailAdminDtoList(inventoryEntryDetails.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(inventoryEntryDetails.getTotalPages());
        responseListDto.setTotalElements(inventoryEntryDetails.getTotalElements());
        return responseListDto;
    }

    @Override
    public List<InventoryEntryDetailDto> getInventoryEntryDetailListAutoComplete(InventoryEntryDetailCriteria inventoryEntryDetailCriteria) {
        List<InventoryEntryDetail> inventoryEntries = inventoryEntryDetailRepository.findAll(inventoryEntryDetailCriteria.getCriteria());
        return inventoryEntryDetailMapper.fromEntityListToInventoryEntryDetailDtoAutoCompleteList(inventoryEntries);
    }

    @Override
    public void createInventoryEntryDetail(CreateInventoryEntryDetailForm createInventoryEntryDetailForm) {
        for (int i = 0; i < createInventoryEntryDetailForm.getInventoryEntryId().length; i++){
            ProductVariation productVariation = productVariationRepository.findById(createInventoryEntryDetailForm.getProductVariationId()[i])
                    .orElseThrow(() -> new NotFoundException("Not found product variation"));
            InventoryEntry inventoryEntry = inventoryEntryRepository.findById(createInventoryEntryDetailForm.getInventoryEntryId()[i])
                    .orElseThrow(() -> new NotFoundException("Not found inventory entry"));
            InventoryEntryDetail inventoryEntryDetail = new InventoryEntryDetail();
            inventoryEntryDetail.setOriginalPrice(createInventoryEntryDetailForm.getOriginalPrice()[i]);
            inventoryEntryDetail.setQuantity(createInventoryEntryDetailForm.getQuantity()[i]);
            inventoryEntryDetail.setProductVariation(productVariation);
            inventoryEntryDetail.setInventoryEntry(inventoryEntry);
            inventoryEntryDetailRepository.save(inventoryEntryDetail);
        }
    }

    @Override
    public void updateInventoryEntryDetail(UpdateInventoryEntryDetailForm updateInventoryEntryDetailForm) {
        for (int i = 0; i < updateInventoryEntryDetailForm.getOriginalPrice().length; i++){
            InventoryEntryDetail inventoryEntryDetail = inventoryEntryDetailRepository.findById(updateInventoryEntryDetailForm.getId()[i])
                    .orElseThrow(() -> new NotFoundException("Not found inventory entry detail"));
            inventoryEntryDetail.setOriginalPrice(updateInventoryEntryDetailForm.getOriginalPrice()[i]);
            inventoryEntryDetail.setQuantity(updateInventoryEntryDetailForm.getQuantity()[i]);
            inventoryEntryDetailRepository.save(inventoryEntryDetail);
        }
    }

    @Override
    public void deleteInventoryEntryDetail(Long id) {
        InventoryEntryDetail inventoryEntryDetail = inventoryEntryDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found inventory entry detail"));
        inventoryEntryDetailRepository.deleteById(id);
    }
}
