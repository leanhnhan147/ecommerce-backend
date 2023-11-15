package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.inventory.InventoryAdminDto;
import com.ecommerce.backend.form.inventory.CreateInventoryForm;
import com.ecommerce.backend.form.inventory.UpdateInventoryForm;
import com.ecommerce.backend.storage.criteria.InventoryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryService {

    InventoryAdminDto getInventoryById(Long id);

    ResponseListDto<List<InventoryAdminDto>> getInventoryList(InventoryCriteria inventoryCriteria, Pageable pageable);

    void createInventory(CreateInventoryForm createInventoryForm);

    void updateInventory(UpdateInventoryForm updateInventoryForm);
}
