package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.Category;
import com.ecommerce.backend.storage.entity.Option;
import com.ecommerce.backend.storage.entity.Permission;
import com.ecommerce.backend.storage.entity.Role;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoleCriteria {

    private Long id;
    private String name;
    private Integer status;
    private Long permissionId;

    public Specification<Role> getCriteria() {
        return new Specification<Role>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(!StringUtils.isEmpty(getName())){
                    predicates.add(cb.like(cb.lower(root.get("name")),"%" + getName() + "%"));
                }

                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                if(getPermissionId() != null){
                    Join<Role, Permission> joinPermission = root.join("permissions", JoinType.INNER);
                    predicates.add((cb.equal((joinPermission.get("id")), getPermissionId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
