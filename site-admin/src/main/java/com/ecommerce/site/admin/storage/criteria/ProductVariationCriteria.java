package com.ecommerce.site.admin.storage.criteria;

import com.ecommerce.site.admin.storage.entity.Product;
import com.ecommerce.site.admin.storage.entity.ProductVariation;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductVariationCriteria {

    private Long id;
    private Integer state;
    private Long productId;

    public Specification<ProductVariation> getCriteria() {
        return new Specification<ProductVariation>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<ProductVariation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(getState() != null){
                    predicates.add(cb.equal(root.get("state"), getState()));
                }

                if(getProductId() != null){
                    Join<ProductVariation, Product> joinProduct = root.join("product", JoinType.INNER);
                    predicates.add((cb.equal((joinProduct.get("id")), getProductId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
