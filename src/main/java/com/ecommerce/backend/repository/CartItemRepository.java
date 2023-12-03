package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long>, JpaSpecificationExecutor<CartItem> {
    CartItem findFirstByProductVariationIdAndCustomerId(Long productVariationId, Long customerId);

    List<CartItem> findByCustomerId(Long customerId);
}
