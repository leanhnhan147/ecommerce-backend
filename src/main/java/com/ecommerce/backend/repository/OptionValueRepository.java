package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.OptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionValueRepository extends JpaRepository<OptionValue, Long>, JpaSpecificationExecutor<OptionValue> {
    OptionValue findByCode(String code);
    OptionValue findByDisplayName(String displayName);
    OptionValue findByIdAndCode(Long id, String code);

    @Query("select ov from OptionValue ov where ov.id in " +
            "(select pvov.optionValue.id from ProductVariationOptionValue pvov where pvov.productVariation.id = :productVariationId)")
    List<OptionValue> findByProductVariationId(@Param("productVariationId") Long productVariationId);
}
