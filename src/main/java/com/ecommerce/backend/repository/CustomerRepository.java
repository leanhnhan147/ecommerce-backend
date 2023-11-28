package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Customer findByUsername(String username);
    Customer findByPhone(String phone);
    Customer findByEmail(String email);
}
