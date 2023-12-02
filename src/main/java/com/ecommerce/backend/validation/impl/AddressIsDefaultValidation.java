package com.ecommerce.backend.validation.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.validation.AddressIsDefault;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddressIsDefaultValidation implements ConstraintValidator<AddressIsDefault, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(AddressIsDefault constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer addressIsDefault, ConstraintValidatorContext constraintValidatorContext) {
        if (addressIsDefault == null && allowNull){
            return true;
        }
        if (!ObjectUtils.equals(addressIsDefault, Constant.ADDRESS_NOT_DEFAULT) &&
                !ObjectUtils.equals(addressIsDefault, Constant.ADDRESS_DEFAULT)){
            return false;
        }
        return true;
    }
}
