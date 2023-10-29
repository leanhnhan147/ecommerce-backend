package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.Category;
import com.ecommerce.backend.storage.entity.GroupPermission;
import com.ecommerce.backend.storage.entity.Option;
import com.ecommerce.backend.storage.entity.Permission;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class PermissionCriteria {
    private Long id;
    private String name;
    private Integer status;
    private Long groupPermissionId;

    public Specification<Permission> getCriteria() {
        return new Specification<Permission>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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

                if(getGroupPermissionId() != null){
                    Join<Permission, GroupPermission> joinGroupPermission = root.join("groupPermission", JoinType.INNER);
                    predicates.add((cb.equal((joinGroupPermission.get("id")), getGroupPermissionId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
