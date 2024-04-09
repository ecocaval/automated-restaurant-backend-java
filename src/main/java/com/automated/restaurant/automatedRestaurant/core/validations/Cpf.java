package com.automated.restaurant.automatedRestaurant.core.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CpfValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Cpf {

    String message() default "O cpf fornecido é invalido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}