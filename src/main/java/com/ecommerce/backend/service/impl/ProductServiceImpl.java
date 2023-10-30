package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.product.ProductAdminDto;
import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.product.CreateProductForm;
import com.ecommerce.backend.form.product.UpdateProductForm;
import com.ecommerce.backend.mapper.ProductMapper;
import com.ecommerce.backend.repository.*;
import com.ecommerce.backend.service.MediaResourceService;
import com.ecommerce.backend.service.ProductService;
import com.ecommerce.backend.storage.criteria.ProductCriteria;
import com.ecommerce.backend.storage.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    ProductOptionRepository productOptionRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    MediaResourceService mediaResourceService;

    @Autowired
    ProductMapper productMapper;

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found product"));
        return productMapper.fromEntityToProductDto(product);
    }

    @Override
    public ResponseListDto<List<ProductAdminDto>> getProductList(ProductCriteria productCriteria, Pageable pageable) {
        Page<Product> products = productRepository.findAll(productCriteria.getCriteria(), pageable);
        ResponseListDto<List<ProductAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(productMapper.fromEntityListToProductAdminDtoList(products.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(products.getTotalPages());
        responseListDto.setTotalElements(products.getTotalElements());
        return responseListDto;
    }

    @Transactional
    @Override
    public void createProduct(CreateProductForm createProductForm) {
        Category category = categoryRepository.findById(createProductForm.getCategoryId()).orElse(null);
        if(category == null) {
            throw new NotFoundException("Not found category");
        }
        Product product = productMapper.fromCreateProductFormToEntity(createProductForm);
        product.setCategory(category);
        productRepository.save(product);

        for (int i = 0; i < createProductForm.getOptions().length; i++){
            Option option = optionRepository.findById(createProductForm.getOptions()[i]).orElse(null);
            if(option != null){
                ProductOption productOption = new ProductOption();
                productOption.setProduct(product);
                productOption.setOption(option);
                productOptionRepository.save(productOption);
            }
        }

        for(int i = 0; i < createProductForm.getImages().length; i++){
            MediaResource mediaResource = mediaResourceService.createMediaResource(createProductForm.getImages()[i]);
            if(mediaResource != null){
                ProductImage productImage = new ProductImage();
                productImage.setProduct(product);
                productImage.setMediaResource(mediaResource);
                productImageRepository.save(productImage);
            }
        }
    }

    @Override
    public void updateProduct(UpdateProductForm updateProductForm) {
        Product product = productRepository.findById(updateProductForm.getId()).orElse(null);
        if(product == null){
            throw new NotFoundException("Not found product");
        }
        productMapper.fromUpdateProductFormToEntity(updateProductForm, product);
        productRepository.save(product);

        for (int i = 0; i < updateProductForm.getOptions().length; i++){
            Option option = optionRepository.findById(updateProductForm.getOptions()[i]).orElse(null);
            if(option != null){
                Boolean existedProductOption = productOptionRepository.existsByProductIdAndOptionId(product.getId(), option.getId());
                if(!existedProductOption){
                    ProductOption productOption = new ProductOption();
                    productOption.setProduct(product);
                    productOption.setOption(option);
                    productOptionRepository.save(productOption);
                }
            }
        }
    }
}
