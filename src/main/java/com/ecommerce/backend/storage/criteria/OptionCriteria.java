package com.ecommerce.backend.storage.criteria;

import com.ecommerce.backend.storage.entity.Category;
import com.ecommerce.backend.storage.entity.Option;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class OptionCriteria {

    private Long id;
    private String name;
    private String displayName;
    private Integer status;
    private Long categoryId;

    public Specification<Option> getCriteria() {
        return new Specification<Option>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Option> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(!StringUtils.isEmpty(getName())){
                    predicates.add(cb.like(cb.lower(root.get("name")),"%" + getName()+ "%"));
                }

                if(!StringUtils.isEmpty(getDisplayName())){
                    predicates.add(cb.like(cb.lower(root.get("displayName")),"%" + getDisplayName() + "%"));
                }

                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                if(getCategoryId() != null){
                    Join<Option, Category> joinCategory = root.join("category", JoinType.INNER);
                    predicates.add((cb.equal((joinCategory.get("id")), getCategoryId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
