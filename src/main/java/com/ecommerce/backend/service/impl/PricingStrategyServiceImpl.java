package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.pricingStrategy.PricingStrategyAdminDto;
import com.ecommerce.backend.dto.pricingStrategy.PricingStrategyDto;
import com.ecommerce.backend.exception.AlreadyExistsException;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.exception.BadRequestException;
import com.ecommerce.backend.form.pricingStrategy.CreatePricingStrategyForm;
import com.ecommerce.backend.form.pricingStrategy.UpdatePricingStrategyForm;
import com.ecommerce.backend.mapper.PricingStrategyMapper;
import com.ecommerce.backend.repository.InventoryEntryRepository;
import com.ecommerce.backend.repository.PricingStrategyRepository;
import com.ecommerce.backend.repository.ProductVariationRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.OTPService;
import com.ecommerce.backend.service.PricingStrategyService;
import com.ecommerce.backend.storage.criteria.PricingStrategyCriteria;
import com.ecommerce.backend.storage.entity.InventoryEntry;
import com.ecommerce.backend.storage.entity.PricingStrategy;
import com.ecommerce.backend.storage.entity.ProductVariation;
import com.ecommerce.backend.storage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PricingStrategyServiceImpl implements PricingStrategyService {

    @Autowired
    private PricingStrategyRepository pricingStrategyRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private InventoryEntryRepository inventoryEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PricingStrategyMapper pricingStrategyMapper;

    @Autowired
    OTPService otpService;

    @Override
    public PricingStrategyAdminDto getPricingStrategyById(Long id) {
        PricingStrategy pricingStrategy = pricingStrategyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found pricing strategy"));
        return pricingStrategyMapper.fromEntityToPricingStrategyAdminDto(pricingStrategy);
    }

    @Override
    public ResponseListDto<List<PricingStrategyAdminDto>> getPricingStrategyList(PricingStrategyCriteria pricingStrategyCriteria, Pageable pageable) {
        Page<PricingStrategy> pricingStrategies = pricingStrategyRepository.findAll(pricingStrategyCriteria.getCriteria(), pageable);
        ResponseListDto<List<PricingStrategyAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(pricingStrategyMapper.fromEntityListToPricingStrategyAdminDtoList(pricingStrategies.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(pricingStrategies.getTotalPages());
        responseListDto.setTotalElements(pricingStrategies.getTotalElements());
        return responseListDto;
    }

    @Transactional
    @Override
    public List<PricingStrategyDto> getPricingStrategyListAutoComplete(PricingStrategyCriteria pricingStrategyCriteria) {
        List<PricingStrategy> pricingStrategies = pricingStrategyRepository.findAll(pricingStrategyCriteria.getCriteria());
        return pricingStrategyMapper.fromEntityListToPricingStrategyAutoCompleteDtoList(pricingStrategies);
    }

    @Transactional
    @Override
    public void createPricingStrategy(CreatePricingStrategyForm createPricingStrategyForm, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user"));
        for(int i = 0; i < createPricingStrategyForm.getProductVariationId().length; i++){
            ProductVariation productVariation = productVariationRepository.findById(createPricingStrategyForm.getProductVariationId()[i])
                    .orElseThrow(() -> new NotFoundException("Not found product variation"));
            InventoryEntry inventoryEntry = inventoryEntryRepository.findById(createPricingStrategyForm.getInventoryEntryId()[i])
                    .orElseThrow(() -> new NotFoundException("Not found inventory entry"));
            PricingStrategy existPricingStrategy = pricingStrategyRepository.findByStartDateAndEndDate(productVariation.getId(), createPricingStrategyForm.getStartDate()[i]).orElse(null);
            if(existPricingStrategy != null) {
                throw new BadRequestException("Start date is invalid");
            } else {
                if(createPricingStrategyForm.getStartDate()[i].compareTo(createPricingStrategyForm.getEndDate()[i]) >= 0) {
                    throw new BadRequestException("End date is invalid");
                }
            }
            PricingStrategy pricingStrategy = new PricingStrategy();
            pricingStrategy.setPrice(createPricingStrategyForm.getPrice()[i]);
            pricingStrategy.setDiscountedPrice(createPricingStrategyForm.getDiscountedPrice()[i]);
            pricingStrategy.setSku(generateSku(createPricingStrategyForm.getSku()[i]));
            pricingStrategy.setStartDate(createPricingStrategyForm.getStartDate()[i]);
            pricingStrategy.setEndDate(createPricingStrategyForm.getEndDate()[i]);
            pricingStrategy.setState(createPricingStrategyForm.getState()[i]);
            pricingStrategy.setProductVariation(productVariation);
            pricingStrategy.setInventoryEntry(inventoryEntry);
            pricingStrategy.setUser(user);
            pricingStrategyRepository.save(pricingStrategy);
        }
    }

        private String generateSku(String sku){
        String generateSku, newSku;
        PricingStrategy pricingStrategy;
        do{
            generateSku = otpService.generate(8);
            newSku = sku + generateSku;
            pricingStrategy = pricingStrategyRepository.findBySku(newSku).orElse(null);
        } while (pricingStrategy != null);
        return newSku;
    }

    @Override
    public void updatePricingStrategy(UpdatePricingStrategyForm updatePricingStrategyForm) {
        for (int i = 0; i < updatePricingStrategyForm.getPrice().length; i++){
            PricingStrategy pricingStrategy = pricingStrategyRepository.findById(updatePricingStrategyForm.getId()[i])
                    .orElseThrow(() -> new NotFoundException("Not found pricing strategy"));
            if(!updatePricingStrategyForm.getStartDate()[i].equals(pricingStrategy.getStartDate())){
                PricingStrategy existPricingStrategy = pricingStrategyRepository.findByPricingStrategyIdAndStartDateAndEndDate(pricingStrategy.getId(), pricingStrategy.getProductVariation().getId(), updatePricingStrategyForm.getStartDate()[i]).orElse(null);
                if(existPricingStrategy != null){
                    throw new BadRequestException("Start date is invalid");
                }
            }
            if(!updatePricingStrategyForm.getEndDate()[i].equals(pricingStrategy.getEndDate())){
                PricingStrategy existPricingStrategy = pricingStrategyRepository.findByPricingStrategyIdAndStartDateAndEndDate(pricingStrategy.getId(), pricingStrategy.getProductVariation().getId(), updatePricingStrategyForm.getEndDate()[i]).orElse(null);
                if(existPricingStrategy != null || updatePricingStrategyForm.getStartDate()[i].compareTo(updatePricingStrategyForm.getEndDate()[i]) >= 0){
                    throw new BadRequestException("End date is invalid");
                }
            }
            pricingStrategy.setPrice(updatePricingStrategyForm.getPrice()[i]);
            pricingStrategy.setDiscountedPrice(updatePricingStrategyForm.getDiscountedPrice()[i]);
            pricingStrategy.setStartDate(updatePricingStrategyForm.getStartDate()[i]);
            pricingStrategy.setEndDate(updatePricingStrategyForm.getEndDate()[i]);
            pricingStrategy.setState(updatePricingStrategyForm.getState()[i]);
            pricingStrategyRepository.save(pricingStrategy);
        }
    }

    @Override
    public void deletePricingStrategy(Long id) {
        PricingStrategy pricingStrategy = pricingStrategyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found pricing strategy"));
        pricingStrategyRepository.deleteById(id);
    }
}
