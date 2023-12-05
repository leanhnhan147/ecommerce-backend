package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.dto.cartItem.CartItemDto;
import com.ecommerce.backend.exception.BadRequestException;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.cartItem.CreateCartItemForm;
import com.ecommerce.backend.form.cartItem.UpdateCartItemForm;
import com.ecommerce.backend.mapper.CartItemMapper;
import com.ecommerce.backend.repository.*;
import com.ecommerce.backend.service.CartService;
import com.ecommerce.backend.storage.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private PricingStrategyRepository pricingStrategyRepository;

    @Autowired
    private OptionValueRepository optionValueRepository;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Transactional
    @Override
    public List<CartItemDto> getCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        List<CartItem> cartItems = cartItemRepository.findByCustomerId(customer.getId());
        List<CartItemDto> cartItemDtos = cartItemMapper.fromEntityListToCartItemDtoList(cartItems);
        for (CartItemDto cartItemDto : cartItemDtos){
            List<OptionValue> optionValues = optionValueRepository.findByProductVariationId(cartItemDto.getProductVariation().getId());
            List<String> optionValueNames = new ArrayList<>();
            for (OptionValue optionValue : optionValues){
                optionValueNames.add(optionValue.getDisplayName());
            }
            cartItemDto.getProductVariation().setOptionValues(optionValueNames);

            PricingStrategy pricingStrategy = pricingStrategyRepository.findPriceByStartDateAndEndDateAndState(cartItemDto.getProductVariation().getId(), new Date(), Constant.PRICING_STRATEGY_STATE_APPLY).orElse(null);
            if(pricingStrategy != null){
                cartItemDto.getProductVariation().setPrice(pricingStrategy.getPrice());
                cartItemDto.getProductVariation().setDiscountedPrice(pricingStrategy.getDiscountedPrice());
            }else {
                cartItemDtos.remove(cartItemDto);
                cartItemRepository.deleteById(cartItemDto.getId());
            }
        }
        return cartItemDtos;
    }

    @Override
    public void createItem(CreateCartItemForm createCartItemForm, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        ProductVariation productVariation = productVariationRepository.findById(createCartItemForm.getProductVariationId())
                .orElseThrow(() -> new NotFoundException("Not found product variation"));
        CartItem cartItem = cartItemRepository.findFirstByProductVariationIdAndCustomerId(productVariation.getId(), customer.getId());
        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + createCartItemForm.getQuantity());
        }else {
            cartItem = new CartItem();
            cartItem.setQuantity(createCartItemForm.getQuantity());
            cartItem.setProductVariation(productVariation);
            cartItem.setCustomer(customer);
        }
        cartItemRepository.save(cartItem);
    }

    @Override
    public void updateItem(UpdateCartItemForm updateCartItemForm, Long customerId) {
        CartItem cartItem = cartItemRepository.findById(updateCartItemForm.getCartItemId())
                .orElseThrow(() -> new NotFoundException("Not found cart item"));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        if(!Objects.equals(cartItem.getCustomer().getId(), customer.getId())){
            throw new BadRequestException("Cart not has this cart item");
        }
        cartItem.setQuantity(updateCartItemForm.getQuantity());
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteItem(Long cartItemId, Long customerId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException("Not found cart item"));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        if(!Objects.equals(cartItem.getCustomer().getId(), customer.getId())){
            throw new BadRequestException("Cart not has this cart item");
        }
        cartItemRepository.deleteById(cartItemId);
    }
}
