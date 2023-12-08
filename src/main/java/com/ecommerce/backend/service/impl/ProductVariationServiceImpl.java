package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationAdminDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.productVariation.CreateProductVariationForm;
import com.ecommerce.backend.form.productVariation.UpdateProductVariationForm;
import com.ecommerce.backend.mapper.ProductVariationMapper;
import com.ecommerce.backend.repository.*;
import com.ecommerce.backend.service.ProductVariationService;
import com.ecommerce.backend.storage.criteria.ProductVariationCriteria;
import com.ecommerce.backend.storage.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProductVariationServiceImpl implements ProductVariationService {

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OptionValueRepository optionValueRepository;

    @Autowired
    ProductVariationOptionValueRepository productVariationOptionValueRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    OptionValueImageRepository optionValueImageRepository;

    @Autowired
    PricingStrategyRepository pricingStrategyRepository;

    @Autowired
    ProductVariationMapper productVariationMapper;

    @Override
    public ProductVariationAdminDto getProductVariationById(Long id) {
        ProductVariation productVariation = productVariationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found product variation"));
        ProductVariationAdminDto productVariationAdminDto = productVariationMapper.fromEntityToProductVariationAdminDto(productVariation);
        productVariationAdminDto.setStock(productVariationRepository.countStockByProductVariationId(productVariation.getId()));

        PricingStrategy pricingStrategy = pricingStrategyRepository.findPriceByStartDateAndEndDateAndState(productVariation.getId(), new Date(), Constant.PRICING_STRATEGY_STATE_APPLY).orElse(null);
        if(pricingStrategy != null){
            productVariationAdminDto.setPrice(pricingStrategy.getPrice());
            productVariationAdminDto.setDiscountedPrice(pricingStrategy.getDiscountedPrice());
        }
        return productVariationAdminDto;
    }

    @Override
    public ResponseListDto<List<ProductVariationAdminDto>> getProductVariationList(ProductVariationCriteria productVariationCriteria, Pageable pageable) {
        Page<ProductVariation> productVariations = productVariationRepository.findAll(productVariationCriteria.getCriteria(), pageable);
        ResponseListDto<List<ProductVariationAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(productVariationMapper.fromEntityListToProductVariationAdminDtoList(productVariations.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(productVariations.getTotalPages());
        responseListDto.setTotalElements(productVariations.getTotalElements());
        return responseListDto;
    }

    @Transactional
    @Override
    public void createProductVariation(CreateProductVariationForm createProductVariationForm) {
        Product product = productRepository.findById(createProductVariationForm.getProductId()).orElse(null);
        if(product == null) {
            throw new NotFoundException("Not found product");
        }

        for(int i = 0; i < createProductVariationForm.getOptionValues().length; i++){
            ProductVariation productVariation = new ProductVariation();
            productVariation.setProduct(product);
            productVariation.setState(createProductVariationForm.getState());
            productVariationRepository.save(productVariation);

            String productVariationName = product.getName();
            Long[] optionValues = createProductVariationForm.getOptionValues()[i];
            for(int j = 0; j < optionValues.length; j++){
                OptionValue optionValue = optionValueRepository.findById(optionValues[j])
                        .orElseThrow(() -> new NotFoundException("Not found option value"));
                productVariationName += " " + optionValue.getDisplayName();
                ProductVariationOptionValue productVariationOptionValue =
                        new ProductVariationOptionValue(productVariation, optionValue);
                productVariationOptionValueRepository.save(productVariationOptionValue);
            }
            productVariation.setName(productVariationName);
            productVariationRepository.save(productVariation);
        }

        for (int i = 0; i < createProductVariationForm.getImageIds().length; i++){
            OptionValue optionValue = optionValueRepository.findById(createProductVariationForm.getImageIds()[i].getOptionValueId())
                    .orElseThrow(() -> new NotFoundException("Not found option value"));
            ProductImage productImage = productImageRepository.findById(createProductVariationForm.getImageIds()[i].getImageId())
                    .orElseThrow(() -> new NotFoundException("Not found image"));
            OptionValueImage optionValueImage = new OptionValueImage(optionValue, productImage);
            optionValueImageRepository.save(optionValueImage);
        }
    }

    // Not completed, update later
    @Override
    public void updateProductVariation(UpdateProductVariationForm updateProductVariationForm) {
        ProductVariation productVariation = productVariationRepository.findById(updateProductVariationForm.getId()).orElse(null);
        if(productVariation == null){
            throw new NotFoundException("Not found product variation");
        }
        productVariationMapper.fromUpdateProductVariationFormToEntity(updateProductVariationForm, productVariation);
        productVariationRepository.save(productVariation);
    }
}
