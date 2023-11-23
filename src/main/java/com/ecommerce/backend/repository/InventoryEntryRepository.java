package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.InventoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InventoryEntryRepository extends JpaRepository<InventoryEntry, Long>, JpaSpecificationExecutor<InventoryEntry> {
    InventoryEntry findByInvoiceCode(String invoiceCode);
}
