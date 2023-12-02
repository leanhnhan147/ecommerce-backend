package com.ecommerce.backend.validation;

import com.ecommerce.backend.validation.impl.AddressIsDefaultValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddressIsDefaultValidation.class)
public @interface AddressIsDefault {
    boolean allowNull() default false;
    String message() default  "Address Default invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
