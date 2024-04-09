package com.automated.restaurant.automatedRestaurant.core.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

    String message() default "O email fornecido é invalido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}