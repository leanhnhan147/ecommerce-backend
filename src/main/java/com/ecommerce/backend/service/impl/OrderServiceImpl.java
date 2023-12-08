package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.dto.cartItem.CartItemDto;
import com.ecommerce.backend.dto.order.CheckoutOrderDto;
import com.ecommerce.backend.dto.order.OrderDto;
import com.ecommerce.backend.dto.orderDetail.OrderDetailDto;
import com.ecommerce.backend.exception.BadRequestException;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.order.CheckoutOrderForm;
import com.ecommerce.backend.form.order.CreateOrderForm;
import com.ecommerce.backend.mapper.*;
import com.ecommerce.backend.repository.*;
import com.ecommerce.backend.service.OrderService;
import com.ecommerce.backend.storage.entity.*;
import com.ecommerce.backend.utils.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderTrackingRepository orderTrackingRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OptionValueRepository optionValueRepository;

    @Autowired
    private PricingStrategyRepository pricingStrategyRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderTrackingMapper orderTrackingMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public CheckoutOrderDto checkoutOrder(CheckoutOrderForm checkoutOrderForm, Long customerId) {
        List<CartItem> cartItems = new ArrayList<>();
        for (int i = 0; i < checkoutOrderForm.getCartItemIds().length; i++){
            CartItem cartItem = cartItemRepository.findById(checkoutOrderForm.getCartItemIds()[i]).orElse(null);
            if(cartItem == null){
                throw new NotFoundException("Not found cart item " + checkoutOrderForm.getCartItemIds()[i]);
            }
            cartItems.add(cartItem);
        }
        List<CartItemDto> cartItemDtos = cartItemMapper.fromEntityListToCartItemDtoList(cartItems);
        Double totalProductPrice = 0.0;
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
                cartItemDto.setTotalPrice(pricingStrategy.getDiscountedPrice()*cartItemDto.getQuantity());
                totalProductPrice += pricingStrategy.getDiscountedPrice()*cartItemDto.getQuantity();
            }
        }
        Address address = addressRepository.findFirstByCustomerIdAndIsDefault(customerId, Constant.ADDRESS_DEFAULT);
        CheckoutOrderDto checkoutOrder = new CheckoutOrderDto();
        checkoutOrder.setShippingPrice(0.0);
        checkoutOrder.setTotalProductPrice(totalProductPrice);
        checkoutOrder.setTotalPrice(checkoutOrder.getShippingPrice() + totalProductPrice);
        checkoutOrder.setAddress(addressMapper.fromEntityToAddressDto(address));
        checkoutOrder.setCartItems(cartItemDtos);
        return checkoutOrder;
    }

    @Override
    public void createOrder(CreateOrderForm createOrderForm, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        Address address = addressRepository.findById(createOrderForm.getAddressId())
                .orElseThrow(() -> new NotFoundException("Not found address"));
        if(!address.getCustomer().getId().equals(customer.getId())){
            throw new BadRequestException("Customer not has this address");
        }
        Order order = new Order();
        order.setShippingPrice(0.0);
        order.setPaymentMethod(createOrderForm.getPaymentMethod());
        order.setState(Constant.ORDER_STATE_WAIT_CONFIRM);
        order.setReceiverName(address.getReceiverName());
        order.setReceiverPhone(address.getReceiverPhone());
        order.setReceiverAddress(getAddressDefault(address));
        order.setCustomer(customer);
        orderRepository.save(order);

        for (int i = 0; i < createOrderForm.getCartItemIds().length; i++){
            CartItem cartItem = cartItemRepository.findById(createOrderForm.getCartItemIds()[i]).orElse(null);
            if(cartItem == null){
                throw new NotFoundException("Not found cart item " + createOrderForm.getCartItemIds()[i]);
            }
            PricingStrategy pricingStrategy = pricingStrategyRepository.findPriceByStartDateAndEndDateAndState(cartItem.getProductVariation().getId(), new Date(), Constant.PRICING_STRATEGY_STATE_APPLY).orElse(null);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setProductVariation(cartItem.getProductVariation());
            orderDetail.setOrder(order);
            orderDetail.setPricingStrategy(pricingStrategy);
            orderDetailRepository.save(orderDetail);
            cartItemRepository.deleteById(createOrderForm.getCartItemIds()[i]);
        }
        OrderTracking orderTracking = new OrderTracking();
        orderTracking.setTitle("Chờ xác nhận đơn hàng");
        orderTracking.setCreatedDate(new Date());
        orderTracking.setState(Constant.ORDER_STATE_WAIT_CONFIRM);
        orderTracking.setOrder(order);
        orderTracking.setCustomer(customer);
        orderTrackingRepository.save(orderTracking);
    }

    private String getAddressDefault(Address address){
        return ZipUtils.zipString(address.getAddressDetail() + ", " + address.getWard().getName() + ", " + address.getDistrict().getName() + ", " + address.getProvince().getName()
                + "|" + address.getProvince().getId() + "|" + address.getDistrict().getId() + "|" + address.getWard().getId());
    }

    @Override
    public OrderDto getOrderDetail(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found order"));
        OrderDto orderDto = orderMapper.fromEntityToOrderDto(order);
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderDto.getId());
        List<OrderDetailDto> orderDetailDtos = orderDetailMapper.fromEntityListToOrderDetailDtoList(orderDetails);
        Double totalProductPrice = 0.0;
        for (int i = 0; i < orderDetailDtos.size(); i++){
            List<OptionValue> optionValues = optionValueRepository.findByProductVariationId(orderDetailDtos.get(i).getProductVariation().getId());
            List<String> optionValueNames = new ArrayList<>();
            for (OptionValue optionValue : optionValues){
                optionValueNames.add(optionValue.getDisplayName());
            }
            orderDetailDtos.get(i).getProductVariation().setOptionValues(optionValueNames);
            PricingStrategy pricingStrategy = pricingStrategyRepository.findById(orderDetails.get(i).getPricingStrategy().getId()).orElse(null);
            if(pricingStrategy != null){
                orderDetailDtos.get(i).getProductVariation().setPrice(pricingStrategy.getPrice());
                orderDetailDtos.get(i).getProductVariation().setDiscountedPrice(pricingStrategy.getDiscountedPrice());
                orderDetailDtos.get(i).setTotalPrice(pricingStrategy.getDiscountedPrice()*orderDetailDtos.get(i).getQuantity());
                totalProductPrice += pricingStrategy.getDiscountedPrice()*orderDetailDtos.get(i).getQuantity();
            }
        }
        orderDto.setTotalProductPrice(totalProductPrice);
        orderDto.setTotalPrice(orderDto.getShippingPrice() + totalProductPrice);
        orderDto.setOrderDetails(orderDetailDtos);

        List<OrderTracking> orderTrackings = orderTrackingRepository.findByOrderId(orderDto.getId());
        orderDto.setOrderTrackings(orderTrackingMapper.fromEntityListToOrderTrackingDtoList(orderTrackings));

        String[] unzip = ZipUtils.unzipString(order.getReceiverAddress()).split("\\|", 4);
        orderDto.setReceiverAddress(unzip[0]);

        return orderDto;
    }

    @Override
    public List<OrderDto> getOrderDetailList(Integer state, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Not found customer"));
        List<Order> orders = orderRepository.findByCustomerIdAndState(customer.getId(), state);
        List<OrderDto> orderDtos = orderMapper.fromEntityListToOrderDtoList(orders);
        for (OrderDto orderDto : orderDtos){
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderDto.getId());
            List<OrderDetailDto> orderDetailDtos = orderDetailMapper.fromEntityListToOrderDetailDtoList(orderDetails);
            Double totalProductPrice = 0.0;
            for (int i = 0; i < orderDetailDtos.size(); i++){
                List<OptionValue> optionValues = optionValueRepository.findByProductVariationId(orderDetailDtos.get(i).getProductVariation().getId());
                List<String> optionValueNames = new ArrayList<>();
                for (OptionValue optionValue : optionValues){
                    optionValueNames.add(optionValue.getDisplayName());
                }
                orderDetailDtos.get(i).getProductVariation().setOptionValues(optionValueNames);
                PricingStrategy pricingStrategy = pricingStrategyRepository.findById(orderDetails.get(i).getPricingStrategy().getId()).orElse(null);
                if(pricingStrategy != null){
                    orderDetailDtos.get(i).getProductVariation().setPrice(pricingStrategy.getPrice());
                    orderDetailDtos.get(i).getProductVariation().setDiscountedPrice(pricingStrategy.getDiscountedPrice());
                    orderDetailDtos.get(i).setTotalPrice(pricingStrategy.getDiscountedPrice()*orderDetailDtos.get(i).getQuantity());
                    totalProductPrice += pricingStrategy.getDiscountedPrice()*orderDetailDtos.get(i).getQuantity();
                }
            }
            orderDto.setTotalPrice(orderDto.getShippingPrice() + totalProductPrice);
            orderDto.setOrderDetails(orderDetailDtos);
        }
        return orderDtos;
    }
}
