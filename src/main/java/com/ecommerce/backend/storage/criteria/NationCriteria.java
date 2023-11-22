package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.Category;
import com.ecommerce.backend.storage.entity.Nation;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class NationCriteria {
    private Long id;
    private String name;
    private String postCode;
    private Integer kind;
    private Integer status;
    private Long parentId;
    private String parentName;

    public Specification<Nation> getCriteria() {
        return new Specification<Nation>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Nation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (!StringUtils.isEmpty(getName())) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getPostCode())) {
                    predicates.add(cb.equal(cb.lower(root.get("postCode")), getPostCode().toLowerCase()));
                }
                if (getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                if (getParentId() != null){
                    Join<Nation, Nation> join = root.join("parent", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getParentId()));
                }
                if (getParentName() != null){
                    Join<Nation, Nation> join = root.join("parent", JoinType.INNER);
                    predicates.add(cb.equal(join.get("name"), getParentName()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
