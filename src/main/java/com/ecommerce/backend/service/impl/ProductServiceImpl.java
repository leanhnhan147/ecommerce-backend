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
import com.ecommerce.backend.dto.review.ReviewDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.product.CreateProductForm;
import com.ecommerce.backend.form.product.UpdateProductForm;
import com.ecommerce.backend.mapper.ProductMapper;
import com.ecommerce.backend.mapper.ReviewMapper;
import com.ecommerce.backend.repository.*;
import com.ecommerce.backend.service.MediaResourceService;
import com.ecommerce.backend.service.ProductService;
import com.ecommerce.backend.storage.criteria.ProductCriteria;
import com.ecommerce.backend.storage.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Autowired
    MediaResourceService mediaResourceService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Value("${file.path.images}")
    private String pathImage;

    @Override
    public ProductFormat getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found product"));
        ProductFormat productFormat = convertProduct(productMapper.fromEntityToProductDto(product));
        productFormat = getRating(productFormat);
        return productFormat;
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
    public ResponseListDto<List<ProductFormat>> getProductFormatList(ProductCriteria productCriteria, Pageable pageable) {
        Page<Product> products;
        if(productCriteria.getCreatedDate() != null && productCriteria.getSoldCount() == null){
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdDate").descending());
            products = productRepository.findAll(productCriteria.getCriteria(), pageable);
        } else if (productCriteria.getCreatedDate() == null && productCriteria.getSoldCount() != null) {
            Pageable pageableBySoldCount = PageRequest.of(0, Integer.MAX_VALUE);
            products = productRepository.findAll(productCriteria.getCriteria(), pageableBySoldCount);
        } else {
            products = productRepository.findAll(productCriteria.getCriteria(), pageable);
        }
        ResponseListDto<List<ProductFormat>> responseListDto = new ResponseListDto<>();
        List<ProductDto> productDtos = productMapper.fromEntityListToProductDtoList(products.getContent());
        List<ProductFormat> productFormats = new ArrayList<>();
        for (ProductDto productDto : productDtos){
            productFormats.add(convertProduct(productDto));
        }
        if(productCriteria.getCreatedDate() == null && productCriteria.getSoldCount() != null){
            Collections.sort(productFormats, Comparator.comparing(ProductFormat::getSoldCount).reversed());
            List<ProductFormat> filteredProductFormats = productFormats.stream()
                    .skip(pageable.getPageNumber() * pageable.getPageSize())
                    .limit(pageable.getPageSize())
                    .collect(Collectors.toList());
            Double totalPages = 1.0*products.getTotalElements()/pageable.getPageSize();
            responseListDto.setContent(filteredProductFormats);
            responseListDto.setTotalPages((int)Math.ceil(totalPages));
        }else {
            responseListDto.setContent(productFormats);
            responseListDto.setTotalPages(products.getTotalPages());
        }
        responseListDto.setPage(pageable.getPageNumber());
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

        Integer soldCount = 0;
        Integer stock = 0;
        for(int i = 0; i < productDto.getProductVariations().size(); i++){
            ProductVariationDto productVariationDto = productDto.getProductVariations().get(i);
            ProductVariationFormat productVariationFormat = new ProductVariationFormat();
            productVariationFormat.setId(productVariationDto.getId());
            Integer productVariationStock = productVariationRepository.countStockByProductVariationId(productVariationDto.getId());
            Integer productVariationSold = orderDetailRepository.countSoldByProductVariationId(productVariationDto.getId(), Constant.ORDER_STATE_DELIVERED);
            Integer templateProductVariationSell = orderDetailRepository.countTemplateSellByProductVariationId(productVariationDto.getId(), Constant.ORDER_STATE_WAIT_CONFIRM, Constant.ORDER_STATE_DELIVERING);
            if(productVariationStock != null && productVariationSold != null){
                soldCount += productVariationSold;
                stock += productVariationStock - productVariationSold;
                productVariationFormat.setStock(productVariationStock - productVariationSold);
                if(templateProductVariationSell != null){
                    stock = stock - templateProductVariationSell;
                    productVariationFormat.setStock(productVariationStock - productVariationSold - templateProductVariationSell);
                }
            } else if (productVariationStock != null && productVariationSold == null) {
                stock += productVariationStock;
                productVariationFormat.setStock(productVariationStock);
                if(templateProductVariationSell != null){
                    stock = stock - templateProductVariationSell;
                    productVariationFormat.setStock(productVariationStock - templateProductVariationSell);
                }
            }

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
        productFormat.setSoldCount(soldCount);
        productFormat.setStock(stock);
        productFormat.setDescription(productDto.getDescription());
        return productFormat;
    }

    private ProductFormat getRating(ProductFormat productFormat){
        List<Review> reviews = reviewRepository.findByProductIdAndState(productFormat.getId(), Constant.REVIEW_STATE_ACTIVE);
        Double totalRating = 0.0;
        Integer rating1 = 0;
        Integer rating2 = 0;
        Integer rating3 = 0;
        Integer rating4 = 0;
        Integer rating5 = 0;
        for (Review review : reviews){
            Integer point = review.getPoint();
            totalRating += point/2.0;
            if(point.equals(1) || point.equals(2)){
                rating1++;
            } else if (point.equals(3) || point.equals(4)){
                rating2++;
            }else if (point.equals(5) || point.equals(6)){
                rating3++;
            }else if (point.equals(7) || point.equals(8)){
                rating4++;
            }else if (point.equals(9) || point.equals(10)){
                rating5++;
            }
        }
        productFormat.setRatingCount(reviews.size());
        productFormat.setAvgRating(totalRating/reviews.size());
        productFormat.setRating1(rating1);
        productFormat.setRating2(rating2);
        productFormat.setRating3(rating3);
        productFormat.setRating4(rating4);
        productFormat.setRating5(rating5);

        List<ReviewDto> reviewDtos = reviewMapper.fromEntityListToReviewDtoList(reviews);
        for (ReviewDto reviewDto : reviewDtos){
            List<ReviewImage> reviewImages = reviewImageRepository.findByReviewId(reviewDto.getId());
            List<String> images = new ArrayList<>();
            for(ReviewImage reviewImage : reviewImages){
                images.add(reviewImage.getMediaResource().getUrl());
            }
            reviewDto.setImages(images);
        }
        productFormat.setReviews(reviewDtos);
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
