package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.Category;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryCriteria {

    private Long id;
    private String name;
    private Integer level;
    private Integer status;
    private Long parentId;

    public Specification<Category> getCriteria() {
        return new Specification<Category>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(getLevel() != null) {
                    predicates.add(cb.equal(root.get("level"), getLevel()));
                }

                if(!StringUtils.isEmpty(getName())){
                    predicates.add(cb.like(cb.lower(root.get("name")),"%"+ getName()+"%"));
                }

                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                if(getParentId() != null){
                    Join<Category, Category> joinParentCategory = root.join("parent", JoinType.INNER);
                    predicates.add((cb.equal((joinParentCategory.get("id")), getParentId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
