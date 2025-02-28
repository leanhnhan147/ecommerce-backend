package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OptionRepository extends JpaRepository<Option, Long>, JpaSpecificationExecutor<Option> {
    Option findByCode(String code);
    Option findByDisplayName(String displayName);
    Option findByIdAndCode(Long id, String code);
}
