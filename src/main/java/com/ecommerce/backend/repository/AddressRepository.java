package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
    Address findFirstByCustomerIdAndIsDefault(Long customerId, Integer isDefault);
}
