package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.Customer;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerCriteria {
    private Long id;
    private String fullName;
    private Integer status;

    public Specification<Customer> getCriteria() {
        return new Specification<Customer>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(!StringUtils.isEmpty(getFullName())){
                    predicates.add(cb.like(cb.lower(root.get("fullName")),"%" + getFullName().toLowerCase() + "%"));
                }

                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
