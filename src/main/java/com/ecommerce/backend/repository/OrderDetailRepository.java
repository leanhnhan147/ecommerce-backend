package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, JpaSpecificationExecutor<OrderDetail> {

    @Query("select sum(od.quantity) from OrderDetail od where od.productVariation.id = :productVariationId " +
            "and od.order.id in (select o.id from Order o where o.state = :stateOrder)")
    Integer countSoldByProductVariationId(@Param("productVariationId") Long productVariationId, @Param("stateOrder") Integer stateOrder);

    List<OrderDetail> findByOrderId(Long orderId);
}
