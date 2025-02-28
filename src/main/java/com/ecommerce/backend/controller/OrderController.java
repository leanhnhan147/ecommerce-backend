package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.order.CheckoutOrderDto;
import com.ecommerce.backend.dto.order.OrderAdminDto;
import com.ecommerce.backend.dto.order.OrderDto;
import com.ecommerce.backend.form.order.CancelOrderForm;
import com.ecommerce.backend.form.order.CheckoutOrderForm;
import com.ecommerce.backend.form.order.CreateOrderForm;
import com.ecommerce.backend.form.order.UpdateStateOrderForm;
import com.ecommerce.backend.service.OrderService;
import com.ecommerce.backend.storage.criteria.OrderCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class OrderController extends BasicController{

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CheckoutOrderDto> create(@Valid @RequestBody CheckoutOrderForm checkoutOrderForm, BindingResult bindingResult) {
        ApiMessageDto<CheckoutOrderDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(orderService.checkoutOrder(checkoutOrderForm, getCurrentUser()));
        apiMessageDto.setMessage("Checkout order success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<OrderDto> create(@Valid @RequestBody CreateOrderForm createOrderForm, BindingResult bindingResult) {
        ApiMessageDto<OrderDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(orderService.createOrder(createOrderForm, getCurrentUser()));
        apiMessageDto.setMessage("Create order success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-state", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateState(@Valid @RequestBody UpdateStateOrderForm updateStateOrderForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        orderService.updateStateOrder(updateStateOrderForm, getCurrentUser());
        apiMessageDto.setMessage("Update state order success");
        return apiMessageDto;
    }

    @PutMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> cancel(@Valid @RequestBody CancelOrderForm cancelOrderForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if(isAdmin()){
            orderService.cancelOrder(cancelOrderForm, null, getCurrentUser());
        }
        if(isCustomer()){
            orderService.cancelOrder(cancelOrderForm, getCurrentUser(), null);
        }
        apiMessageDto.setMessage("Cancel order success");
        return apiMessageDto;
    }

    @GetMapping(value = "/get-detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<OrderDto> getDetail(@PathVariable("id") Long id) {
        ApiMessageDto<OrderDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(orderService.getOrderDetail(id));
        apiMessageDto.setMessage("Get order success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<OrderDto>>> getListOrder(OrderCriteria orderCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<OrderDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(orderService.getListOrder(orderCriteria, pageable, getCurrentUser()));
        responseListDtoApiMessageDto.setMessage("Get list order success");
        return responseListDtoApiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<OrderAdminDto>>> getList(OrderCriteria orderCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<OrderAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(orderService.getList(orderCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list order success");
        return responseListDtoApiMessageDto;
    }
}
