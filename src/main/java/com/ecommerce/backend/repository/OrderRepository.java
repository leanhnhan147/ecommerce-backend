package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Optional<Order> findByIdAndState(Long id, Integer state);

    List<Order> findByCustomerId(Long customerId);
    List<Order> findByCustomerIdAndState(Long customerId, Integer state);
}
