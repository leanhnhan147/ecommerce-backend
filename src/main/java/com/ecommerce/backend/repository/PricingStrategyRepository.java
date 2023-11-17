package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.PricingStrategy;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface PricingStrategyRepository extends JpaRepository<PricingStrategy, Long>, JpaSpecificationExecutor<PricingStrategy> {

    @Query("select ps from PricingStrategy ps " +
            "where ps.productVariation.id = :productVariationId " +
            "and ps.startDate <= :nowDate " +
            "and ps.endDate >= :nowDate " +
            "and ps.state = :state")
    Optional<PricingStrategy> findPriceByStartDateAndEndDateAndState(@Param("productVariationId") Long  productVariationId,
                                                                     @Param("nowDate") Date nowDate,
                                                                     @Param("state") Integer state);

    @Query("select ps from PricingStrategy ps " +
            "where ps.productVariation.id = :productVariationId " +
            "and ps.endDate in (select max(ps1.endDate) " +
                "from PricingStrategy ps1 " +
                "where ps1.productVariation.id = :productVariationId " +
                "and ps1.endDate < :nowDate " +
                "and ps1.state = :state)  ")
    Optional<PricingStrategy> findPriceByEndDateAndState(@Param("productVariationId") Long  productVariationId,
                                                         @Param("nowDate") Date nowDate,
                                                         @Param("state") Integer state);
}
