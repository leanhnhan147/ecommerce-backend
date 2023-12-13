package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.order.CheckoutOrderDto;
import com.ecommerce.backend.dto.order.OrderAdminDto;
import com.ecommerce.backend.dto.order.OrderDto;
import com.ecommerce.backend.form.order.CancelOrderForm;
import com.ecommerce.backend.form.order.CheckoutOrderForm;
import com.ecommerce.backend.form.order.CreateOrderForm;
import com.ecommerce.backend.form.order.UpdateStateOrderForm;
import com.ecommerce.backend.storage.criteria.OrderCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    CheckoutOrderDto checkoutOrder(CheckoutOrderForm checkoutOrderForm, Long customerId);

    OrderDto createOrder(CreateOrderForm createOrderForm, Long customerId);

    void updateStateOrder(UpdateStateOrderForm updateStateOrderForm, Long userId);

    void cancelOrder(CancelOrderForm cancelOrderForm, Long customerId, Long userId);

    OrderDto getOrderDetail(Long id);

    ResponseListDto<List<OrderDto>> getListOrder(OrderCriteria orderCriteria, Pageable pageable, Long customerId);

    ResponseListDto<List<OrderAdminDto>> getList(OrderCriteria orderCriteria, Pageable pageable);
}
