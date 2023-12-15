package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.*;
import com.ecommerce.backend.storage.entity.Order;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewCriteria {
    private Long id;
    private Integer point;
    private Integer state;
    private Long productId;
    private Long productVariationId;
    private Long customerId;
    private Long orderId;

    public Specification<Review> getCriteria() {
        return new Specification<Review>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(getState() != null){
                    predicates.add(cb.equal(root.get("state"), getState()));
                }

                if(getProductId() != null){
                    predicates.add((cb.equal((root.get("productVariation").get("product").get("id")), getProductId())));
                }

                if(getProductVariationId() != null){
                    Join<Review, ProductVariation> joinProductVariation = root.join("productVariation", JoinType.INNER);
                    predicates.add((cb.equal((joinProductVariation.get("id")), getProductVariationId())));
                }

                if(getCustomerId() != null){
                    Join<Review, Customer> joinCustomer = root.join("customer", JoinType.INNER);
                    predicates.add((cb.equal((joinCustomer.get("id")), getCustomerId())));
                }

                if(getOrderId() != null){
                    Join<Review, Order> joinOrder = root.join("order", JoinType.INNER);
                    predicates.add((cb.equal((joinOrder.get("id")), getOrderId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
