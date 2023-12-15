package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.Category;
import com.ecommerce.backend.storage.entity.Product;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductCriteria {

    private Long id;
    private String name;
    private Integer status;
    private Long categoryId;

    // sort
    private Integer createdDate;
    private Integer soldCount;

    public Specification<Product> getCriteria() {
        return new Specification<Product>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(!StringUtils.isEmpty(getName())){
                    predicates.add(cb.like(cb.lower(root.get("name")),"%"+ getName().toLowerCase()+"%"));
                }

                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                if(getCategoryId() != null){
                    Join<Product, Category> joinCategory = root.join("category", JoinType.INNER);
                    predicates.add((cb.equal((joinCategory.get("id")), getCategoryId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
