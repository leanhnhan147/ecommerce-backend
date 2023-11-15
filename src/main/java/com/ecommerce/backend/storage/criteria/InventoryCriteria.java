package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class InventoryCriteria {
    private Long id;
    private Integer status;
    private Long productVariationId;
    private Long userId;

    public Specification<Inventory> getCriteria() {
        return new Specification<Inventory>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                if(getProductVariationId() != null){
                    Join<Inventory, ProductVariation> joinProductVariation = root.join("productVariation", JoinType.INNER);
                    predicates.add((cb.equal((joinProductVariation.get("id")), getProductVariationId())));
                }

                if(getUserId() != null){
                    Join<Inventory, User> joinUser = root.join("user", JoinType.INNER);
                    predicates.add((cb.equal((joinUser.get("id")), getUserId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
