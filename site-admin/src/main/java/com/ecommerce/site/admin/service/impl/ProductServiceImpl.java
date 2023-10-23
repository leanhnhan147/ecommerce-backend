package com.ecommerce.site.admin.service.impl;

import com.ecommerce.site.admin.dto.ResponseListDto;
import com.ecommerce.site.admin.dto.product.ProductAdminDto;
import com.ecommerce.site.admin.dto.product.ProductDto;
import com.ecommerce.site.admin.exception.NotFoundException;
import com.ecommerce.site.admin.form.product.CreateProductForm;
import com.ecommerce.site.admin.form.product.UpdateProductForm;
import com.ecommerce.site.admin.mapper.ProductMapper;
import com.ecommerce.site.admin.repository.CategoryRepository;
import com.ecommerce.site.admin.repository.OptionRepository;
import com.ecommerce.site.admin.repository.ProductOptionRepository;
import com.ecommerce.site.admin.repository.ProductRepository;
import com.ecommerce.site.admin.service.ProductService;
import com.ecommerce.site.admin.storage.criteria.ProductCriteria;
import com.ecommerce.site.admin.storage.entity.Category;
import com.ecommerce.site.admin.storage.entity.Option;
import com.ecommerce.site.admin.storage.entity.Product;
import com.ecommerce.site.admin.storage.entity.ProductOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
                ProductOption productOption = new ProductOption();
                productOption.setProduct(product);
                productOption.setOption(option);
                productOptionRepository.save(productOption);
            }
        }
    }
}
