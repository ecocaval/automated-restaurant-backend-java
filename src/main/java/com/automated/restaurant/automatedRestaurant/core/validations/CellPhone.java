package com.automated.restaurant.automatedRestaurant.core.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CellPhoneValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CellPhone {

    String message() default "O celular só pode conter 9 dígitos numéricos.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}