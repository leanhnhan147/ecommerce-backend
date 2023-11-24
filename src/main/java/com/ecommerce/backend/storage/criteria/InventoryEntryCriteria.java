package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class InventoryEntryCriteria {
    private Long id;
    private String invoiceCode;
    private Integer status;
    private Long providerId;
    private Long userId;

    public Specification<InventoryEntry> getCriteria() {
        return new Specification<InventoryEntry>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<InventoryEntry> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(!StringUtils.isEmpty(getInvoiceCode())){
                    predicates.add(cb.like(cb.lower(root.get("invoiceCode")),"%" + getInvoiceCode().toLowerCase() + "%"));
                }

                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                if(getProviderId() != null){
                    Join<InventoryEntry, Provider> joinProvider = root.join("provider", JoinType.INNER);
                    predicates.add((cb.equal((joinProvider.get("id")), getProviderId())));
                }

                if(getUserId() != null){
                    Join<InventoryEntry, User> joinUser = root.join("user", JoinType.INNER);
                    predicates.add((cb.equal((joinUser.get("id")), getUserId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
