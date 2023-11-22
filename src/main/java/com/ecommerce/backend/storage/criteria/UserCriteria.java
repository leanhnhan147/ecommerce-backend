package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.Category;
import com.ecommerce.backend.storage.entity.Option;
import com.ecommerce.backend.storage.entity.Role;
import com.ecommerce.backend.storage.entity.User;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserCriteria {

    private Long id;
    private String fullName;
    private Integer status;
    private Long roleId;

    public Specification<User> getCriteria() {
        return new Specification<User>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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

                if(getRoleId() != null){
                    Join<User, Role> joinRole = root.join("role", JoinType.INNER);
                    predicates.add((cb.equal((joinRole.get("id")), getRoleId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
