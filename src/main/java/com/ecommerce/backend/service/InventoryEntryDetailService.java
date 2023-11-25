package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.inventoryEntryDetail.InventoryEntryDetailAdminDto;
import com.ecommerce.backend.dto.inventoryEntryDetail.InventoryEntryDetailDto;
import com.ecommerce.backend.form.inventoryEntryDetail.CreateInventoryEntryDetailForm;
import com.ecommerce.backend.form.inventoryEntryDetail.UpdateInventoryEntryDetailForm;
import com.ecommerce.backend.storage.criteria.InventoryEntryDetailCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryEntryDetailService {

    InventoryEntryDetailAdminDto getInventoryEntryDetailById(Long id);

    ResponseListDto<List<InventoryEntryDetailAdminDto>> getInventoryEntryDetailList(InventoryEntryDetailCriteria inventoryEntryDetailCriteria, Pageable pageable);

    List<InventoryEntryDetailDto> getInventoryEntryDetailListAutoComplete(InventoryEntryDetailCriteria inventoryEntryDetailCriteria);

    void createInventoryEntryDetail(CreateInventoryEntryDetailForm createInventoryEntryDetailForm);

    void updateInventoryEntryDetail(UpdateInventoryEntryDetailForm updateInventoryEntryDetailForm);

    void deleteInventoryEntryDetail(Long id);
}
