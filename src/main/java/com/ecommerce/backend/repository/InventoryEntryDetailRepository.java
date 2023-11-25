package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.InventoryEntryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InventoryEntryDetailRepository extends JpaRepository<InventoryEntryDetail, Long>, JpaSpecificationExecutor<InventoryEntryDetail> {
}
