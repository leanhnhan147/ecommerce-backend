package com.ecommerce.site.admin.service.impl;

import com.ecommerce.site.admin.dto.ResponseListDto;
import com.ecommerce.site.admin.dto.productVariation.ProductVariationAdminDto;
import com.ecommerce.site.admin.exception.NotFoundException;
import com.ecommerce.site.admin.form.productVariation.CreateProductVariationForm;
import com.ecommerce.site.admin.form.productVariation.UpdateProductVariationForm;
import com.ecommerce.site.admin.mapper.ProductVariationMapper;
import com.ecommerce.site.admin.repository.OptionValueRepository;
import com.ecommerce.site.admin.repository.ProductRepository;
import com.ecommerce.site.admin.repository.ProductVariationOptionValueRepository;
import com.ecommerce.site.admin.repository.ProductVariationRepository;
import com.ecommerce.site.admin.service.ProductVariationService;
import com.ecommerce.site.admin.storage.criteria.ProductVariationCriteria;
import com.ecommerce.site.admin.storage.entity.OptionValue;
import com.ecommerce.site.admin.storage.entity.Product;
import com.ecommerce.site.admin.storage.entity.ProductVariation;
import com.ecommerce.site.admin.storage.entity.ProductVariationOptionValue;
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
        ProductVariation productVariation = productVariationMapper.fromCreateProductVariationFormToEntity(createProductVariationForm);
        productVariation.setProduct(product);
        productVariationRepository.save(productVariation);

        for(int i = 0; i < createProductVariationForm.getOptionValues().length; i++){
            OptionValue optionValue = optionValueRepository.findById(createProductVariationForm.getOptionValues()[i]).orElse(null);
            if(optionValue != null){
                ProductVariationOptionValue productVariationOptionValue = new ProductVariationOptionValue();
                productVariationOptionValue.setProductVariation(productVariation);
                productVariationOptionValue.setOptionValue(optionValue);
                productVariationOptionValueRepository.save(productVariationOptionValue);
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

        for(int i = 0; i < updateProductVariationForm.getOptionValues().length; i++){
            OptionValue optionValue = optionValueRepository.findById(updateProductVariationForm.getOptionValues()[i]).orElse(null);
            if(optionValue != null){
                ProductVariationOptionValue productVariationOptionValue = new ProductVariationOptionValue();
                productVariationOptionValue.setProductVariation(productVariation);
                productVariationOptionValue.setOptionValue(optionValue);
                productVariationOptionValueRepository.save(productVariationOptionValue);
            }
        }
    }
}
