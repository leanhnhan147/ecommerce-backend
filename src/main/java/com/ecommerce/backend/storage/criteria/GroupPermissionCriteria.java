package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.GroupPermission;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class GroupPermissionCriteria {

    private Long id;
    private String name;
    private Integer status;

    public Specification<GroupPermission> getCriteria() {
        return new Specification<GroupPermission>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<GroupPermission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(!StringUtils.isEmpty(getName())){
                    predicates.add(cb.like(cb.lower(root.get("name")),"%"+ getName().toLowerCase() + "%"));
                }

                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
