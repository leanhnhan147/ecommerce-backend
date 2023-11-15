package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.inventory.InventoryAdminDto;
import com.ecommerce.backend.dto.option.OptionAdminDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.inventory.CreateInventoryForm;
import com.ecommerce.backend.form.inventory.UpdateInventoryForm;
import com.ecommerce.backend.mapper.InventoryMapper;
import com.ecommerce.backend.repository.InventoryRepository;
import com.ecommerce.backend.repository.ProductVariationRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.InventoryService;
import com.ecommerce.backend.storage.criteria.InventoryCriteria;
import com.ecommerce.backend.storage.entity.Inventory;
import com.ecommerce.backend.storage.entity.Option;
import com.ecommerce.backend.storage.entity.ProductVariation;
import com.ecommerce.backend.storage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InventoryMapper inventoryMapper;

    @Override
    public InventoryAdminDto getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found inventory"));
        return inventoryMapper.fromEntityToInventoryAdminDto(inventory);
    }

    @Override
    public ResponseListDto<List<InventoryAdminDto>> getInventoryList(InventoryCriteria inventoryCriteria, Pageable pageable) {
        Page<Inventory> inventories = inventoryRepository.findAll(inventoryCriteria.getCriteria(), pageable);
        ResponseListDto<List<InventoryAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(inventoryMapper.fromEntityListToInventoryAdminDtoList(inventories.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(inventories.getTotalPages());
        responseListDto.setTotalElements(inventories.getTotalElements());
        return responseListDto;
    }

    @Override
    public void createInventory(CreateInventoryForm createInventoryForm) {
        ProductVariation productVariation = productVariationRepository.findById(createInventoryForm.getProductVariationId())
                .orElseThrow(() -> new NotFoundException("Not found product variation"));
        User user = userRepository.findById(createInventoryForm.getUserId())
                .orElseThrow(() -> new NotFoundException("Not found user"));
        Inventory inventory = inventoryMapper.fromCreateInventoryFormToEntity(createInventoryForm);
        inventory.setProductVariation(productVariation);
        inventory.setUser(user);
        inventoryRepository.save(inventory);
    }

    @Override
    public void updateInventory(UpdateInventoryForm updateInventoryForm) {
        Inventory inventory = inventoryRepository.findById(updateInventoryForm.getId())
                .orElseThrow(() -> new NotFoundException("Not found inventory"));
        inventoryMapper.fromUpdateInventoryFormToEntity(updateInventoryForm, inventory);
        inventoryRepository.save(inventory);
    }
}
