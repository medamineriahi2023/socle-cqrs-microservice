package com.oga.cqrsref.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmptyValidator  implements ConstraintValidator<NotEmpty, Object> {
        public void initialize(NotEmpty constraintAnnotation) {

        }

        public boolean isValid(Object value, ConstraintValidatorContext context) {
            return value != null && !value.toString().isEmpty();
        }

    }