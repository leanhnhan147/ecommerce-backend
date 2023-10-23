package com.ecommerce.site.admin.repository;

import com.ecommerce.site.admin.storage.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OptionRepository extends JpaRepository<Option, Long>, JpaSpecificationExecutor<Option> {
}
