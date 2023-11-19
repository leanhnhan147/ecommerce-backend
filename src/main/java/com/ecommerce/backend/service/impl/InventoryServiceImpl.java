package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.inventory.InventoryAdminDto;
import com.ecommerce.backend.dto.inventory.InventoryDto;
import com.ecommerce.backend.dto.option.OptionAdminDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.inventory.CreateInventoryForm;
import com.ecommerce.backend.form.inventory.UpdateInventoryForm;
import com.ecommerce.backend.mapper.InventoryMapper;
import com.ecommerce.backend.repository.InventoryRepository;
import com.ecommerce.backend.repository.ProductVariationRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.InventoryService;
import com.ecommerce.backend.service.OTPService;
import com.ecommerce.backend.storage.criteria.InventoryCriteria;
import com.ecommerce.backend.storage.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    OTPService otpService;

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
    public List<InventoryDto> getInventoryListAutoComplete(InventoryCriteria inventoryCriteria) {
        List<Inventory> inventories = inventoryRepository.findAll(inventoryCriteria.getCriteria());
        return inventoryMapper.fromEntityListToInventoryAutoCompleteDtoList(inventories);
    }

    @Override
    public void createInventory(CreateInventoryForm createInventoryForm) {
        User user = userRepository.findById(createInventoryForm.getUserId())
                .orElseThrow(() -> new NotFoundException("Not found user"));

        for(int i = 0; i < createInventoryForm.getProductVariationId().length; i++){
            ProductVariation productVariation = productVariationRepository.findById(createInventoryForm.getProductVariationId()[i])
                    .orElseThrow(() -> new NotFoundException("Not found product variation"));
            Inventory inventory = new Inventory();
            inventory.setOriginalPrice(createInventoryForm.getOriginalPrice()[i]);
            inventory.setQuantity(createInventoryForm.getQuantity()[i]);
            inventory.setSku(generateSku(createInventoryForm.getSku()[i]));
            inventory.setImportTime(new Date());
            inventory.setProductVariation(productVariation);
            inventory.setUser(user);
            inventoryRepository.save(inventory);
        }
    }

    private String generateSku(String sku){
        String generateSku, newSku;
        Inventory inventory;
        do{
            generateSku = otpService.generate(8);
            newSku = sku + generateSku;
            inventory = inventoryRepository.findBySku(newSku).orElse(null);
        } while (inventory != null);
        return newSku;
    }

    // Not completed, update later
    @Override
    public void updateInventory(UpdateInventoryForm updateInventoryForm) {
        Inventory inventory = inventoryRepository.findById(updateInventoryForm.getId())
                .orElseThrow(() -> new NotFoundException("Not found inventory"));
        inventoryMapper.fromUpdateInventoryFormToEntity(updateInventoryForm, inventory);
        inventoryRepository.save(inventory);
    }

    @Override
    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found inventory"));
        inventoryRepository.deleteById(id);
    }
}
