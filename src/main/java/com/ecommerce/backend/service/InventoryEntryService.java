package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryAdminDto;
import com.ecommerce.backend.dto.inventoryEntry.InventoryEntryDto;
import com.ecommerce.backend.form.inventoryEntry.CreateInventoryEntryForm;
import com.ecommerce.backend.form.inventoryEntry.UpdateInventoryEntryForm;
import com.ecommerce.backend.storage.criteria.InventoryEntryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryEntryService {

    InventoryEntryAdminDto getInventoryEntryById(Long id);

    ResponseListDto<List<InventoryEntryAdminDto>> getInventoryEntryList(InventoryEntryCriteria inventoryEntryCriteria, Pageable pageable);

    List<InventoryEntryDto> getInventoryEntryListAutoComplete(InventoryEntryCriteria inventoryEntryCriteria);

    void createInventoryEntry(CreateInventoryEntryForm createInventoryEntryForm, Long userId);

    void updateInventoryEntry(UpdateInventoryEntryForm updateInventoryEntryForm);

    void deleteInventoryEntry(Long id);
}
