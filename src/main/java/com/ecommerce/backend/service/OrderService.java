package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.order.CheckoutOrderDto;
import com.ecommerce.backend.dto.order.OrderDto;
import com.ecommerce.backend.form.order.CancelOrderForm;
import com.ecommerce.backend.form.order.CheckoutOrderForm;
import com.ecommerce.backend.form.order.CreateOrderForm;
import com.ecommerce.backend.form.order.UpdateStateOrderForm;

import java.util.List;

public interface OrderService {

    CheckoutOrderDto checkoutOrder(CheckoutOrderForm checkoutOrderForm, Long customerId);

    OrderDto createOrder(CreateOrderForm createOrderForm, Long customerId);

    void updateStateOrder(UpdateStateOrderForm updateStateOrderForm, Long userId);

    void cancelOrder(CancelOrderForm cancelOrderForm, Long customerId, Long userId);

    OrderDto getOrderDetail(Long id);

    List<OrderDto> getOrderDetailList(Integer state, Long customerId);
}
