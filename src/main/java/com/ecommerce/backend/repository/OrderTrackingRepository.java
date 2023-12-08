package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.OrderTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderTrackingRepository extends JpaRepository<OrderTracking, Long>, JpaSpecificationExecutor<OrderTracking> {
    List<OrderTracking> findByOrderId(Long orderId);
}
