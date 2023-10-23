package com.ecommerce.site.admin.storage.criteria;

import com.ecommerce.site.admin.storage.entity.Option;
import com.ecommerce.site.admin.storage.entity.OptionValue;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class OptionValueCriteria {

    private Long id;
    private String value;
    private String displayName;
    private Integer status;
    private Long optionId;

    public Specification<OptionValue> getCriteria() {
        return new Specification<OptionValue>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<OptionValue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(getId() != null){
                    predicates.add(cb.equal(root.get("id"), getId()));
                }

                if(!StringUtils.isEmpty(getValue())){
                    predicates.add(cb.like(cb.lower(root.get("value")),"%" + getValue()+ "%"));
                }

                if(!StringUtils.isEmpty(getDisplayName())){
                    predicates.add(cb.like(cb.lower(root.get("displayName")),"%" + getDisplayName() + "%"));
                }

                if(getStatus() != null){
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                if(getOptionId() != null){
                    Join<OptionValue, Option> joinOption = root.join("option", JoinType.INNER);
                    predicates.add((cb.equal((joinOption.get("id")), getOptionId())));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
