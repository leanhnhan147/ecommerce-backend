package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.Order;
import com.ecommerce.backend.storage.entity.Customer;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderCriteria {
    private Long id;
    private Integer state;
    private Long customerId;

    public Specification<Order> getCriteria() {
        return new Specification<Order>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(getState() != null){
                    predicates.add(cb.equal(root.get("state"), getState()));
                }

                if(getCustomerId() != null){
                    Join<Order, Customer> joinCustomer = root.join("customer", JoinType.INNER);
                    predicates.add((cb.equal((joinCustomer.get("id")), getCustomerId())));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
