package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.order.CheckoutOrderDto;
import com.ecommerce.backend.dto.order.OrderDto;
import com.ecommerce.backend.form.order.CheckoutOrderForm;
import com.ecommerce.backend.form.order.CreateOrderForm;

import java.util.List;

public interface OrderService {

    CheckoutOrderDto checkoutOrder(CheckoutOrderForm checkoutOrderForm, Long customerId);

    void createOrder(CreateOrderForm createOrderForm, Long customerId);

    OrderDto getOrderDetail(Long id);

    List<OrderDto> getOrderDetailList(Integer state, Long customerId);
}
