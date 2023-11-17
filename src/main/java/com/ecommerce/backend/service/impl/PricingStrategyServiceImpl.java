package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.pricingStrategy.PricingStrategyAdminDto;
import com.ecommerce.backend.dto.pricingStrategy.PricingStrategyDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.exception.RequestException;
import com.ecommerce.backend.form.pricingStrategy.CreatePricingStrategyForm;
import com.ecommerce.backend.form.pricingStrategy.UpdatePricingStrategyForm;
import com.ecommerce.backend.mapper.PricingStrategyMapper;
import com.ecommerce.backend.repository.PricingStrategyRepository;
import com.ecommerce.backend.repository.ProductVariationRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.PricingStrategyService;
import com.ecommerce.backend.storage.criteria.PricingStrategyCriteria;
import com.ecommerce.backend.storage.entity.Category;
import com.ecommerce.backend.storage.entity.PricingStrategy;
import com.ecommerce.backend.storage.entity.ProductVariation;
import com.ecommerce.backend.storage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PricingStrategyServiceImpl implements PricingStrategyService {

    @Autowired
    PricingStrategyRepository pricingStrategyRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PricingStrategyMapper pricingStrategyMapper;

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

    @Override
    public List<PricingStrategyDto> getPricingStrategyListAutoComplete(PricingStrategyCriteria pricingStrategyCriteria) {
        List<PricingStrategy> pricingStrategies = pricingStrategyRepository.findAll(pricingStrategyCriteria.getCriteria());
        return pricingStrategyMapper.fromEntityListToPricingStrategyAutoCompleteDtoList(pricingStrategies);

    }

    @Override
    public void createPricingStrategy(CreatePricingStrategyForm createPricingStrategyForm) {
        ProductVariation productVariation = productVariationRepository.findById(createPricingStrategyForm.getProductVariationId())
                .orElseThrow(() -> new NotFoundException("Not found product variation"));
        User user = userRepository.findById(createPricingStrategyForm.getUserId())
                .orElseThrow(() -> new NotFoundException("Not found user"));
        checkStartDateAndEndDate(productVariation.getId(), createPricingStrategyForm.getStartDate(), createPricingStrategyForm.getEndDate());
        PricingStrategy pricingStrategy = pricingStrategyMapper.fromCreatePricingStrategyFormToEntity(createPricingStrategyForm);
        pricingStrategy.setProductVariation(productVariation);
        pricingStrategy.setUser(user);
        pricingStrategyRepository.save(pricingStrategy);
    }

    @Override
    public void updatePricingStrategy(UpdatePricingStrategyForm updatePricingStrategyForm) {
        PricingStrategy pricingStrategy = pricingStrategyRepository.findById(updatePricingStrategyForm.getId())
                .orElseThrow(() -> new NotFoundException("Not found pricing strategy"));
//        if(!updatePricingStrategyForm.getStartDate().equals(pricingStrategy.getStartDate())){
//            checkStartDateAndEndDate(pricingStrategy.getProductVariation().getId(), updatePricingStrategyForm.getStartDate(), updatePricingStrategyForm.getEndDate());
//        } else {
//            if(!updatePricingStrategyForm.getEndDate().equals(pricingStrategy.getEndDate())
//            && updatePricingStrategyForm.getStartDate().compareTo(updatePricingStrategyForm.getEndDate()) >= 0){
//                throw new RequestException("End date is invalid");
//            }
//        }

        pricingStrategyMapper.fromUpdatePricingStrategyFormToEntity(updatePricingStrategyForm, pricingStrategy);
        pricingStrategyRepository.save(pricingStrategy);
    }

    private void checkStartDateAndEndDate(Long productVariationId, Date startDate, Date endDate) {
        PricingStrategy pricingStrategy = pricingStrategyRepository.findPriceByEndDate(productVariationId, startDate).orElse(null);
        if(pricingStrategy != null) {
            throw new RequestException("Start date is invalid");
        } else {
            if(startDate.compareTo(endDate) >= 0) {
                throw new RequestException("End date is invalid");
            }
        }
    }

    @Override
    public void deletePricingStrategy(Long id) {
        PricingStrategy pricingStrategy = pricingStrategyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found pricing strategy"));
        pricingStrategyRepository.deleteById(id);
    }
}
