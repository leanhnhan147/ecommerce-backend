package com.ecommerce.backend.validation.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.validation.NationKind;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NationKindValidation implements ConstraintValidator<NationKind,Integer> {
    private boolean allowNull;

    @Override
    public void initialize(NationKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer nationKind, ConstraintValidatorContext constraintValidatorContext) {
        if(nationKind == null && allowNull) {
            return true;
        }
        if(!Objects.equals(nationKind, Constant.NATION_KIND_PROVINCE) &&
                !Objects.equals(nationKind, Constant.NATION_KIND_DISTRICT) &&
                !Objects.equals(nationKind, Constant.NATION_KIND_WARD)) {
            return false;
        }
        return true;
    }
}
