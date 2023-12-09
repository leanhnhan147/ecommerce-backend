package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, JpaSpecificationExecutor<OrderDetail> {

    @Query("select sum(od.quantity) from OrderDetail od where od.productVariation.id = :productVariationId")
    Integer countSoldByProductVariationId(@Param("productVariationId") Long productVariationId);

    List<OrderDetail> findByOrderId(Long orderId);
}
