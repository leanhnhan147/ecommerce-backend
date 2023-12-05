package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.constant.Constant;
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
import com.ecommerce.backend.dto.product.ProductIdDto;
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
    ProductImageRepository productImageRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    PricingStrategyRepository pricingStrategyRepository;

    @Autowired
    MediaResourceService mediaResourceService;

    @Autowired
    ProductMapper productMapper;

    @Value("${file.path.images}")
    private String pathImage;

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
    public ProductIdDto createProduct(CreateProductForm createProductForm) {
        Category category = categoryRepository.findById(createProductForm.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Not found category"));
        Product product = productMapper.fromCreateProductFormToEntity(createProductForm);
        product.setCategory(category);
        product.setOptions(createProductOption(product, createProductForm.getOptionIds()));
        productRepository.save(product);


        List<Long> imageIds = new ArrayList<>();
        if(createProductForm.getImages() != null){
            MediaResource mediaResource = mediaResourceService.createMediaResource(pathImage, createProductForm.getImages()[0]);
            if(mediaResource == null){
                throw new NotFoundException("Not found media resource");
            }
            ProductImage productImage = new ProductImage(product, mediaResource);
            productImageRepository.save(productImage);
            product.setAvatar(mediaResource.getUrl());
            productRepository.save(product);
            imageIds.add(productImage.getId());
        }

        for(int i = 1; i < createProductForm.getImages().length; i++){
            MediaResource mediaResource = mediaResourceService.createMediaResource(pathImage, createProductForm.getImages()[i]);
            if(mediaResource == null){
                throw new NotFoundException("Not found media resource");
            }
            ProductImage productImage = new ProductImage(product, mediaResource);
            productImageRepository.save(productImage);
            imageIds.add(productImage.getId());
        }

        ProductIdDto productIdDto = new ProductIdDto();
        productIdDto.setProductId(product.getId());
        productIdDto.setImageIds(imageIds);

        return productIdDto;
    }

    // Not completed, update later
    @Transactional
    @Override
    public void updateProduct(UpdateProductForm updateProductForm) {
        Product product = productRepository.findById(updateProductForm.getId())
                .orElseThrow(() -> new NotFoundException("Not found product"));
        productMapper.fromUpdateProductFormToEntity(updateProductForm, product);
        product.setOptions(createProductOption(product, updateProductForm.getOptionIds()));
        productRepository.save(product);
    }

    private List<Option> createProductOption(Product product, Long[] optionIds){
        List<Option> options = new ArrayList<>();
        for (int i = 0; i < optionIds.length; i++){
            Option option = optionRepository.findById(optionIds[i])
                    .orElseThrow(() -> new NotFoundException("Not found option"));
            options.add(option);
        }
        return options;
    }

    private ProductFormat convertProduct(ProductDto productDto){
        ProductFormat productFormat = new ProductFormat();

        List<ProductImageFormat> productImageFormats = new ArrayList<>();

        List<ProductVariationFormat> productVariationFormats = new ArrayList<>();

        List<OptionFormat> optionFormats = new ArrayList<>();
        List<String> optionFormatCodes = new ArrayList<>();
        List<OptionValueFormat> optionValueFormats = new ArrayList<>();
        List<String> optionValueFormatCodes = new ArrayList<>();

        for(int i = 0; i < productDto.getProductVariations().size(); i++){
            ProductVariationDto productVariationDto = productDto.getProductVariations().get(i);
            ProductVariationFormat productVariationFormat = new ProductVariationFormat();
            productVariationFormat.setStock(productVariationRepository.countStockByProductVariationId(productVariationDto.getId()));

            PricingStrategy pricingStrategy = pricingStrategyRepository.findPriceByStartDateAndEndDateAndState(productVariationDto.getId(), new Date(), Constant.PRICING_STRATEGY_STATE_APPLY).orElse(null);
            if(pricingStrategy != null){
                productVariationFormat.setPrice(pricingStrategy.getPrice());
                productVariationFormat.setDiscountedPrice(pricingStrategy.getDiscountedPrice());
            }

            List<String> optionValueIds = new ArrayList<>();
            for(int j = 0; j < productVariationDto.getProductVariationOptionValues().size(); j++){
                OptionValueDto optionValueDto = productVariationDto.getProductVariationOptionValues().get(j).getOptionValue();
                optionValueIds.add(optionValueDto.getCode());

                // Check OptionValueFormat
                if(!optionValueFormatCodes.contains(optionValueDto.getCode())){
                    optionValueFormatCodes.add(optionValueDto.getCode());

                    OptionValueFormat optionValueFormat = new OptionValueFormat();
                    optionValueFormat.setCode(optionValueDto.getCode());
                    optionValueFormat.setDisplayName(optionValueDto.getDisplayName());
                    optionValueFormat.setOptionId(optionValueDto.getOption().getCode());
                    optionValueFormats.add(optionValueFormat);
                }

                // Check OptionFormat
                OptionDto optionDto = optionValueDto.getOption();
                if(!optionFormatCodes.contains(optionDto.getCode())){
                    optionFormatCodes.add(optionDto.getCode());

                    OptionFormat optionFormat = new OptionFormat();
                    optionFormat.setCode(optionDto.getCode());
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
                    if(optionValueFormats.get(j).getCode().equals(productImageDto.getOptionValueImage().getOptionValue().getCode())){
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
                            int index = optionFormatCodes.indexOf(optionValueFormat.getOptionId());
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
        productFormat.setName(productDto.getName());
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
