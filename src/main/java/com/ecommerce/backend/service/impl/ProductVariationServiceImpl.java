package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationAdminDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.productVariation.CreateProductVariationForm;
import com.ecommerce.backend.form.productVariation.UpdateProductVariationForm;
import com.ecommerce.backend.mapper.ProductVariationMapper;
import com.ecommerce.backend.repository.OptionValueRepository;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.repository.ProductVariationOptionValueRepository;
import com.ecommerce.backend.repository.ProductVariationRepository;
import com.ecommerce.backend.service.ProductVariationService;
import com.ecommerce.backend.storage.criteria.ProductVariationCriteria;
import com.ecommerce.backend.storage.entity.OptionValue;
import com.ecommerce.backend.storage.entity.Product;
import com.ecommerce.backend.storage.entity.ProductVariation;
import com.ecommerce.backend.storage.entity.ProductVariationOptionValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    ProductVariationMapper productVariationMapper;

    @Override
    public ProductVariationAdminDto getProductVariationById(Long id) {
        ProductVariation productVariation = productVariationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found product variation"));
        return productVariationMapper.fromEntityToProductVariationAdminDto(productVariation);
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

    @Override
    public void createProductVariation(CreateProductVariationForm createProductVariationForm) {
        Product product = productRepository.findById(createProductVariationForm.getProductId()).orElse(null);
        if(product == null) {
            throw new NotFoundException("Not found product");
        }

        for(int i = 0; i < createProductVariationForm.getOptionValues().length; i++){
            ProductVariation productVariation = new ProductVariation();
            productVariation.setPrice(createProductVariationForm.getPrice()[i]);
            productVariation.setProduct(product);
            productVariation.setState(createProductVariationForm.getState());
            productVariationRepository.save(productVariation);

            Long[] optionValues = createProductVariationForm.getOptionValues()[i];
            for(int j = 0; j < optionValues.length; j++){
                OptionValue optionValue = optionValueRepository.findById(optionValues[j]).orElse(null);
                if(optionValue != null) {
                    ProductVariationOptionValue productVariationOptionValue = new ProductVariationOptionValue();
                    productVariationOptionValue.setProductVariation(productVariation);
                    productVariationOptionValue.setOptionValue(optionValue);
                    productVariationOptionValueRepository.save(productVariationOptionValue);
                }
            }
        }
    }

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
