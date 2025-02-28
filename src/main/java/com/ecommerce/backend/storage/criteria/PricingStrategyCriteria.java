package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.InventoryEntry;
import com.ecommerce.backend.storage.entity.PricingStrategy;
import com.ecommerce.backend.storage.entity.ProductVariation;
import com.ecommerce.backend.storage.entity.User;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class PricingStrategyCriteria {
    private Long id;
    private String sku;
    private Integer state;
    private Integer status;
    private Long productVariationId;
    private Long inventoryEntryId;
    private Long userId;

    public Specification<PricingStrategy> getCriteria() {
        return new Specification<PricingStrategy>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<PricingStrategy> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(!StringUtils.isEmpty(getSku())){
                    predicates.add(cb.like(cb.lower(root.get("sku")),"%" + getSku().toLowerCase() + "%"));
                }

                if(getState() != null){
                    predicates.add(cb.equal(root.get("state"), getState()));
                }

                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                if(getProductVariationId() != null){
                    Join<PricingStrategy, ProductVariation> joinProductVariation = root.join("productVariation", JoinType.INNER);
                    predicates.add((cb.equal((joinProductVariation.get("id")), getProductVariationId())));
                }

                if(getInventoryEntryId() != null){
                    Join<PricingStrategy, InventoryEntry> joinInventoryEntry = root.join("inventoryEntry", JoinType.INNER);
                    predicates.add((cb.equal((joinInventoryEntry.get("id")), getInventoryEntryId())));
                }

                if(getUserId() != null){
                    Join<PricingStrategy, User> joinUser = root.join("user", JoinType.INNER);
                    predicates.add((cb.equal((joinUser.get("id")), getUserId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
