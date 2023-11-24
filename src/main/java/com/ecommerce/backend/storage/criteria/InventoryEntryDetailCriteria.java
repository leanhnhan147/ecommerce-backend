package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class InventoryEntryDetailCriteria {
    private Long id;
    private Integer status;
    private Long productVariationId;
    private Long inventoryEntryId;

    public Specification<InventoryEntryDetail> getCriteria() {
        return new Specification<InventoryEntryDetail>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<InventoryEntryDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                if(getProductVariationId() != null){
                    Join<InventoryEntryDetail, ProductVariation> joinProductVariation = root.join("productVariation", JoinType.INNER);
                    predicates.add((cb.equal((joinProductVariation.get("id")), getProductVariationId())));
                }

                if(getInventoryEntryId() != null){
                    Join<InventoryEntryDetail, InventoryEntry> joinInventoryEntry = root.join("inventoryEntry", JoinType.INNER);
                    predicates.add((cb.equal((joinInventoryEntry.get("id")), getInventoryEntryId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
