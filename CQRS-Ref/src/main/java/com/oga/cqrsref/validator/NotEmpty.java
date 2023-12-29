package com.oga.cqrsref.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { EmptyValidator.class })
public @interface NotEmpty {
    String message() default "Le champ ne doit pas être vide.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
