package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.pricingStrategy.PricingStrategyAdminDto;
import com.ecommerce.backend.dto.pricingStrategy.PricingStrategyDto;
import com.ecommerce.backend.form.pricingStrategy.CreatePricingStrategyForm;
import com.ecommerce.backend.form.pricingStrategy.UpdatePricingStrategyForm;
import com.ecommerce.backend.storage.criteria.PricingStrategyCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PricingStrategyService {

    PricingStrategyAdminDto getPricingStrategyById(Long id);

    ResponseListDto<List<PricingStrategyAdminDto>> getPricingStrategyList(PricingStrategyCriteria pricingStrategyCriteria, Pageable pageable);

    List<PricingStrategyDto> getPricingStrategyListAutoComplete(PricingStrategyCriteria pricingStrategyCriteria);

    void createPricingStrategy(CreatePricingStrategyForm createPricingStrategyForm);

    void updatePricingStrategy(UpdatePricingStrategyForm updatePricingStrategyForm);

    void deletePricingStrategy(Long id);
}
