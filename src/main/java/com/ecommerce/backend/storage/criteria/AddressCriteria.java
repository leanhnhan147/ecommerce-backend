package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.Address;
import com.ecommerce.backend.storage.entity.Customer;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddressCriteria {
    private Long id;
    private Integer isDefault;
    private Integer status;
    private Long customerId;

    public Specification<Address> getCriteria() {
        return new Specification<Address>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(getIsDefault() != null){
                    predicates.add(cb.equal(root.get("isDefault"), getIsDefault()));
                }

                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                if(getCustomerId() != null){
                    Join<Address, Customer> joinCustomer = root.join("customer", JoinType.INNER);
                    predicates.add(cb.equal(joinCustomer.get("id"), getCustomerId()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
