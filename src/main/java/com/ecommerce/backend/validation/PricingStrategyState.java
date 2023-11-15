package com.ecommerce.backend.validation;

import com.ecommerce.backend.validation.impl.PricingStrategyStateValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PricingStrategyStateValidation.class)
public @interface PricingStrategyState {
    boolean allowNull() default false;
    String message() default "Invalid Pricing Strategy state";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
