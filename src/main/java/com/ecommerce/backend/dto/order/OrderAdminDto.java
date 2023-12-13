package com.ecommerce.backend.dto.order;

import com.ecommerce.backend.dto.BasicAdminDto;
import com.ecommerce.backend.dto.orderDetail.OrderDetailDto;
import com.ecommerce.backend.dto.orderTracking.OrderTrackingDto;
import lombok.Data;

import java.util.List;

@Data
public class OrderAdminDto extends BasicAdminDto {
    private Double shippingPrice;
    private Double totalProductPrice;
    private Double totalPrice;
    private Integer state;
    private Integer paymentMethod;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private List<OrderDetailDto> orderDetails;
    private List<OrderTrackingDto> orderTrackings;
}
