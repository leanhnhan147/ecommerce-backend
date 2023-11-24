package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long>, JpaSpecificationExecutor<ProductVariation> {

    @Query("select sum(ied.quantity) from InventoryEntryDetail ied where ied.productVariation.id = :productVariationId")
    Integer countStockByProductVariationId(@Param("productVariationId") Long productVariationId);
}
