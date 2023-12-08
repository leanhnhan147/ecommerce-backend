package com.ecommerce.backend.dto.orderTracking;

import lombok.Data;

import java.util.Date;

@Data
public class OrderTrackingDto {
    private Long id;
    private String title;
    private String note;
    private Integer state;
    private Date createdDate;
}
