package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.category.CategoryDto;
import com.ecommerce.backend.dto.format.option.OptionFormat;
import com.ecommerce.backend.dto.format.optionValue.OptionValueFormat;
import com.ecommerce.backend.dto.format.product.ProductFormat;
import com.ecommerce.backend.dto.format.productImage.ProductImageFormat;
import com.ecommerce.backend.dto.format.productVariation.ProductVariationFormat;
import com.ecommerce.backend.dto.option.OptionDto;
import com.ecommerce.backend.dto.optionValue.OptionValueDto;
import com.ecommerce.backend.dto.product.ProductAdminDto;
import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.dto.productImage.ProductImageDto;
import com.ecommerce.backend.dto.productVariation.ProductVariationDto;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    @Value("${file.path.product-images}")
    private String path;

    @Override
    public ProductFormat getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found product"));
//        ProductFormat productFormat = convertProduct(productMapper.fromEntityToProductDto(product));
//        return productMapper.fromEntityToProductDto(product);
        return convertProduct(productMapper.fromEntityToProductDto(product));
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

        for (int i = 0; i < createProductForm.getOptionIds().length; i++){
            Option option = optionRepository.findById(createProductForm.getOptionIds()[i]).orElse(null);
            if(option != null){
                ProductOption productOption = new ProductOption(product, option);
                productOptionRepository.save(productOption);
            }
        }

        for(int i = 0; i < createProductForm.getImages().length; i++){
            MediaResource mediaResource = mediaResourceService.createMediaResource(path, createProductForm.getImages()[i]);
            if(mediaResource != null){
                ProductImage productImage = new ProductImage(product, mediaResource);
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

    private ProductFormat convertProduct(ProductDto productDto){
        ProductFormat productFormat = new ProductFormat();

        List<ProductImageFormat> productImageFormats = new ArrayList<>();

        List<ProductVariationFormat> productVariationFormats = new ArrayList<>();

        List<OptionFormat> optionFormats = new ArrayList<>();
        List<Long> optionFormatIds = new ArrayList<>();
        List<OptionValueFormat> optionValueFormats = new ArrayList<>();
        List<Long> optionValueFormatIds = new ArrayList<>();

        for(int i = 0; i < productDto.getProductVariations().size(); i++){
            ProductVariationDto productVariationDto = productDto.getProductVariations().get(i);
            ProductVariationFormat productVariationFormat = new ProductVariationFormat();
            productVariationFormat.setPrice(productVariationDto.getPrice());
            productVariationFormat.setStock(productVariationDto.getStock());

            List<Long> optionValueIds = new ArrayList<>();
            for(int j = 0; j < productVariationDto.getProductVariationOptionValues().size(); j++){
                OptionValueDto optionValueDto = productVariationDto.getProductVariationOptionValues().get(j).getOptionValue();
                optionValueIds.add(optionValueDto.getId());

                // Check OptionValueFormat
                if(!optionValueFormatIds.contains(optionValueDto.getId())){
                    optionValueFormatIds.add(optionValueDto.getId());

                    OptionValueFormat optionValueFormat = new OptionValueFormat();
                    optionValueFormat.setId(optionValueDto.getId());
                    optionValueFormat.setDisplayName(optionValueDto.getDisplayName());
                    optionValueFormat.setOptionId(optionValueDto.getOption().getId());
                    optionValueFormats.add(optionValueFormat);
                }

                // Check OptionFormat
                OptionDto optionDto = optionValueDto.getOption();
                if(!optionFormatIds.contains(optionDto.getId())){
                    optionFormatIds.add(optionDto.getId());

                    OptionFormat optionFormat = new OptionFormat();
                    optionFormat.setId(optionDto.getId());
                    optionFormat.setDisplayName(optionDto.getDisplayName());
                    optionFormats.add(optionFormat);
                }
            }
            productVariationFormat.setOptionValues(optionValueIds);
            productVariationFormats.add(productVariationFormat);
        }

        for(int i = 0; i < productDto.getProductImages().size(); i++){
            ProductImageDto productImageDto = productDto.getProductImages().get(i);
            ProductImageFormat productImageFormat = new ProductImageFormat();
            productImageFormat.setId(productImageDto.getMediaResource().getId());
            productImageFormat.setSrc(productImageDto.getMediaResource().getUrl());
            productImageFormats.add(productImageFormat);

            if(productImageDto.getOptionValueImage() != null){
                for(int j = 0; j < optionValueFormats.size(); j++){
                    if(optionValueFormats.get(j).getId().equals(productImageDto.getOptionValueImage().getOptionValue().getId())){
                        optionValueFormats.get(j).setImageId(productImageDto.getMediaResource().getId());
                    }
                }
            }
        }

//        List<List<OptionValueFormat>> optionValueFormatsGroup = optionValueFormats
//                .stream()
//                .collect(Collectors.groupingBy(OptionValueFormat::getOptionId))
//                .values()
//                .stream()
//                .map(ArrayList::new)
//                .collect(Collectors.toList());

        List<List<OptionValueFormat>> optionValueFormatsGroup = optionValueFormats.stream()
                .collect(Collectors.groupingBy(OptionValueFormat::getOptionId, LinkedHashMap::new, Collectors.toList()))
                .values()
                .stream()
                .map(group -> group.stream()
                        .sorted(Comparator.comparing(optionValueFormat -> {
                            int index = optionFormatIds.indexOf(optionValueFormat.getOptionId());
                            return index != -1 ? index : Integer.MAX_VALUE;
                        }))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        productFormat.setImages(productImageFormats);
        productFormat.setVariations(productVariationFormats);
        productFormat.setOptions(optionFormats);
        productFormat.setOptionValueGroups(optionValueFormatsGroup);

        List<String> categories = getCategoryNames(productDto.getCategory());
        Collections.reverse(categories);
        productFormat.setCategories(categories);

        productFormat.setId(productDto.getId());
        productFormat.setTitle(productDto.getName());
        productFormat.setAvgRating(productDto.getAverageRating());
        productFormat.setRatingCount(productDto.getRatingCount());
        productFormat.setSoldCount(productDto.getSoldCount());
        productFormat.setDescription(productDto.getDescription());
        return productFormat;
    }

    private List<String> getCategoryNames(CategoryDto categoryDto){
        List<String> categorieNames = new ArrayList<>();
        categorieNames.add(categoryDto.getName());
        if(categoryDto.getParent() != null){
            categorieNames.addAll(getCategoryNames(categoryDto.getParent()));
        }
        return categorieNames;
    }
}
