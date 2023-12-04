package com.ecommerce.backend.validation.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.validation.PricingStrategyState;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PricingStrategyStateValidation implements ConstraintValidator<PricingStrategyState, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(PricingStrategyState constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer pricingStrategyState, ConstraintValidatorContext constraintValidatorContext) {
        if (pricingStrategyState == null && allowNull){
            return true;
        }
        if (!ObjectUtils.equals(pricingStrategyState, Constant.PRICING_STRATEGY_STATE_NOT_APPLY) &&
                !ObjectUtils.equals(pricingStrategyState, Constant.PRICING_STRATEGY_STATE_APPLY)){
            return false;
        }
        return true;
    }
}
